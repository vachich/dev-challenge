(ns app.service-test
  (:require [clojure.test :refer :all]
            [expectations.clojure.test :refer :all]
            [app.service :as service])
  (:import [clojure.lang ExceptionInfo]))

(deftest compute-test
  (expecting "throws ExceptionInfo if invalid input"
    (expect ExceptionInfo (service/compute {}))
    (expect ExceptionInfo (service/compute {:address {:values [11 -11]}}))
    (expect ExceptionInfo (service/compute {:address {:values []}})))
  (expecting "returns a map with correct computation"
    (expect {:result 4} (service/compute {:address {:values [11 11]}})))
  (expecting "success even with a big number"
    (expect {:result 75} (service/compute {:address {:values [2312312312312131231 1231231231232312321]}}))))

(deftest check-valid-values-test
  (expecting "throws ExceptionInfo if invalid"
    (expect ExceptionInfo (#'service/check-valid-values [11 -11]))
    (expect ExceptionInfo (#'service/check-valid-values [1 11.0]))
    (expect ExceptionInfo (#'service/check-valid-values [1 "1"]))
    (expect ExceptionInfo (#'service/check-valid-values [])))
  (expecting "returns nil if valid"
    (expect nil (#'service/check-valid-values [11 11]))))

(deftest pos-int-or-zero?-test
  (expect true (#'service/pos-int-or-zero? 10))
  (expect true (#'service/pos-int-or-zero? 0))
  (expect false (#'service/pos-int-or-zero? -1))
  (expect false (#'service/pos-int-or-zero? 10.1)))

(deftest number->digits-test
  (expecting "converts number to vector of digits"
    (expect [1 1] (#'service/number->digits 11))))
