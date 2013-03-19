(ns naipmoro.qrng.core-test
    (:require [clojure.test :refer :all]
              [naipmoro.qrng.core :refer :all]
              [criterium.core :refer [bench]]
              [midje.sweet :refer :all]))

(comment
  
  (require '[naipmoro.qrng.core-test :refer :all] 
           '[clojure.test :refer :all]
           '[naipmoro.qrng.core :refer :all]
           '[criterium.core :refer [bench]]
           '[midje.sweet :refer :all])

  (require '[naipmoro.qrng.core :refer :all]
           '[naipmoro.qrng.core-test :refer :all] :reload-all)
  
  ) ;end comment

(comment

  "A down-and-dirty way of getting 1024 random alphanumeric characters
   (including underscore):"

  (second (re-find #"cellpadding=\"\d{1,2}\">\s\n<tr>\n<td>\n(.*?)<"
                   (:body (http/get "http://qrng.anu.edu.au/RawChar.php"))))
  )

(defn hex? [s]
  (let [n (Integer/parseInt s 16)]
    (and (>= n 0) (< n 16))))

(defn hex16? [s]
  (let [n (Integer/parseInt s 16)]
    (and (>= n 0) (< n 256))))

(defn hex32? [s]
  (let [n (Integer/parseInt s 16)]
    (and (>= n 0) (< n 65536))))

(defn hex-char? [c]
  (re-find #"[0-9a-f]" (str c)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fact 
 (let [v (qrand)]
   v => (has every? #(and (>= % 0) (< % 256)))
   v => #(= 1 (count %))))

(fact 
 (let [v (qrand :length 5)]
   v => (has every? #(and (>= % 0) (< % 256)))
   v => #(= 5 (count %))))

(fact 
 (let [v (qrand :length 8 :type :int16)]
   v => (has every? #(and (>= % 0) (< % 65536)))
   v => #(= 8 (count %))))

(fact 
 (let [v (qrand :type :hex16)]
   v => (has every? hex16?)
   v => #(= 1 (count %))))

(fact 
 (let [v (qrand :type :hex16 :length 11)]
   v => (has every? hex16?)
   v => #(= 11 (count %))))

(fact 
 (let [v (qrand :type :hex16 :length 13 :blocks 2)]
   v => (has every? hex32?)
   v => #(= 13 (count %))))

(fact 
 (let [v (qrand :type :hex16 :blocks 1024)]
   (apply seq v) => (has every? hex-char?)
   (apply seq v) => #(= 2048 (count %))
   v => #(= 1 (count %))))
