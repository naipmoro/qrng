(ns naipmoro.qrng.core
  (:require [clj-http.client :as http]
            [cheshire.core :as cheshire]))

(def anu-url "https://qrng.anu.edu.au/API/jsonI.php")
(def ^:const maxlen 1024) ; determined by the ANU server
(def types {:int8 "uint8", :int16 "uint16", :hex16 "hex16"})

(defn quot-rem [num denom]
  [(quot num denom) (rem num denom)])

(defn parse-data [s]
  (get (cheshire/parse-string s) "data"))

(defn qrand
  "Returns a vector of random numbers generated by the
   ANU Random Numbers Server.

   The ':length' keyword determines the number of random numbers to be
   returned. The default is 1.

   You have 3 choices for keyword ':type':
   :int8  (returns integers between 0-255; the default)
   :int16 (returns integers between 0-65535)
   :hex16 (returns hexadecimal strings between 00-ff)

   The ':blocks' keyword is relevant only for type :hex16. It sets the
   hexadecimal block size and must be a number between 1-1024. The default
   is 1.

   Note: If the requested vector is larger than the server's maximum (1024),
   a connection pool is used to concat multiple requests."
  
  [& {:keys [type length blocks]
      :or {type   :int8     ; 
           length 1         ; the defaults
           blocks 1   }}]   ;
  {:pre [(pos? length)                        ; length > 0
         (and (> blocks 0) (< blocks 1025))]} ; 0 < blocks <= 1024
  (let [base-query {"type" (type types) "size" blocks}]
    (if (> length maxlen)
      (let [[q r] (quot-rem length maxlen), v (atom [])]
        (http/with-connection-pool
          (dotimes [_ q]
            (->> {:query-params (assoc base-query "length" maxlen)}
                 (http/get anu-url)
                 :body
                 parse-data
                 (swap! v concat)))
          (when (> r 0)
            (->> {:query-params (assoc base-query "length" r)}
                 (http/get anu-url)
                 :body
                 parse-data
                 (swap! v concat)))
          (vec (deref v))))
                                        ; else if length <= maxlen
      (->> {:query-params (assoc base-query "length" length)}
           (http/get anu-url)
           :body
           parse-data))))

