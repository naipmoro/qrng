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
 (let [h (qrand)]
   h => (has every? #(and (>= % 0) (< % 256)))
   h => #(= 1 (count %))))

(fact 
 (let [h (qrand :length 5)]
   h => (has every? #(and (>= % 0) (< % 256)))
   h => #(= 5 (count %))))

(fact 
 (let [h (qrand :length 8 :type :int16)]
   h => (has every? #(and (>= % 0) (< % 65536)))
   h => #(= 8 (count %))))

(fact 
 (let [h (qrand :type :hex16)]
   h => (has every? hex16?)
   h => #(= 1 (count %))))

(fact 
 (let [h (qrand :type :hex16 :length 11)]
   h => (has every? hex16?)
   h => #(= 11 (count %))))

(fact 
 (let [h (qrand :type :hex16 :length 13 :blocks 2)]
   h => (has every? hex32?)
   h => #(= 13 (count %))))

(fact 
 (let [h (qrand :type :hex16 :blocks 1024)]
   (apply seq h) => (has every? hex-char?)
   (apply seq h) => #(= 2048 (count %))
   h => #(= 1 (count %))))
