(ns naipmoro.qrng.core-test
    (:require [clojure.test :refer :all]
              [naipmoro.qrng.core :refer :all]))

(comment
  
  (require '[naipmoro.qrng.core-test :refer :all] 
           '[clojure.test :refer :all]
           '[naipmoro.qrng.core :refer :all]
           '[criterium.core :refer [bench]])

  (require '[naipmoro.qrng.core :refer :all]
           '[naipmoro.qrng.core-test :refer :all] :reload-all)
  
  ) ;end comment

(comment

  "A down-and-dirty way of getting 1024 random alphanumeric characters
   (including underscore):"

  (second (re-find #"cellpadding=\"\d{1,2}\">\s\n<tr>\n<td>\n(.*?)<"
                   (:body (http/get "http://qrng.anu.edu.au/RawChar.php"))))
  )

(defn hex16? [s]
  (let [n (Integer/parseInt s 16)]
    (and (>= n 0) (< n 256))))

(defn hex32? [s]
  (let [n (Integer/parseInt s 16)]
    (and (>= n 0) (< n 65536))))

(defn hex-char? [c]
  (re-find #"[0-9a-f]" (str c)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest consecutive-gets-test
  (testing "basic usage"
    (let [v1 (qrand)
          v2 (qrand :length 5001)
          v3 (qrand :length 8 :type :int16)
          v4 (qrand :type :hex16)
          v5 (qrand :type :hex16 :length 11)
          v6 (qrand :type :hex16 :length 13 :blocks 2)
          v7 (qrand :type :hex16 :blocks 1024)
          v7chars (apply seq v7)]
      (is (every? #(and (>= % 0) (< % 256)) v1))
      (is (= 1 (count v1)))
      (is (every? #(and (>= % 0) (< % 256)) v2))
      (is (= 5001 (count v2)))
      (is (every? #(and (>= % 0) (< % 65536)) v3))
      (is (= 8 (count v3)))
      (is (every? hex16? v4))
      (is (= 1 (count v4)))
      (is (every? hex16? v5))
      (is (= 11 (count v5)))
      (is (every? hex32? v6))
      (is (= 13 (count v6)))
      (is (every? hex-char? v7chars))
      (is (= 2048 (count v7chars)))
      (is (= 1 (count v7))))))

(deftest t1
  (let [v1 (qrand)]
    (is (every? #(and (>= % 0) (< % 256)) v1))
    (is (= 1 (count v1)))))

(deftest t2
  (let [v2 (qrand :length 5001)]
    (is (every? #(and (>= % 0) (< % 256)) v2))
    (is (= 5001 (count v2)))))

(deftest t3
  (let [v3 (qrand :length 8 :type :int16)]
    (is (every? #(and (>= % 0) (< % 65536)) v3))
    (is (= 8 (count v3)))))

(deftest t4
  (let [v4 (qrand :type :hex16)]
    (is (every? hex16? v4))
    (is (= 1 (count v4)))))

(deftest t5
  (let [v5 (qrand :type :hex16 :length 11)]
    (is (every? hex16? v5))
    (is (= 11 (count v5)))))

(deftest t6
  (let [v6 (qrand :type :hex16 :length 13 :blocks 2)]
    (is (every? hex32? v6))
    (is (= 13 (count v6)))))

(deftest t7
  (let [v7 (qrand :type :hex16 :blocks 1024)
        v7chars (apply seq v7)]
    (is (every? hex-char? v7chars))
    (is (= 2048 (count v7chars)))
    (is (= 1 (count v7)))))

