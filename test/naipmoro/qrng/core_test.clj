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

(deftest test-fix-me
  (is (= 1 2)))

(comment

  "A down-and-dirty way of getting

  1) 1024 bytes of random hexadecimals in string form:"

  (second (re-find #"cellpadding=\"\d{1,2}\">\s\n<tr>\n<td>\n(.*?)<"
                   (:body (http/get "http://qrng.anu.edu.au/RawHex.php"))))

  "2) 1024 randon bits in string form:"

  (second (re-find #"cellpadding=\"\d{1,2}\">\s\n<tr>\n<td>\n(.*?)<"
                   (:body (http/get "http://qrng.anu.edu.au/RawBin.php"))))

  "3) 1024 random alphanumeric characters (including underscore):"

  (second (re-find #"cellpadding=\"\d{1,2}\">\s\n<tr>\n<td>\n(.*?)<"
                   (:body (http/get "http://qrng.anu.edu.au/RawChar.php"))))
  )
