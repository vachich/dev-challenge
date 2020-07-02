(ns app.service-test
  (:require [clojure.test :refer :all]
            [expectations.clojure.test :refer :all]
            [app.service :refer :all])
  (:import [clojure.lang ExceptionInfo]))

(deftest compute-test
  (expecting "throws ExceptionInfo if invalid input"
    (expect ExceptionInfo (compute {}))
    (expect ExceptionInfo (compute {:address {:values [11 -11]}})))
  (expecting "returns a map with correct computation"
    (expect {:result 4} (compute {:address {:values [11 11]}}))))

(deftest check-valid-values-test
  (expecting "throws ExceptionInfo if invalid"
    (expect ExceptionInfo (#'check-valid-values [11 -11]))
    (expect ExceptionInfo (#'check-valid-values [1 11.0]))
    (expect ExceptionInfo (#'check-valid-values [1 "1"])))
  (expecting "returns nil if valid"
    (expect nil (#'check-valid-values [11 11]))))

(deftest pos-int-or-zero?-test
  (expect true (#'pos-int-or-zero? 10))
  (expect true (#'pos-int-or-zero? 0))
  (expect false (#'pos-int-or-zero? -1))
  (expect false (#'pos-int-or-zero? 10.1)))

(deftest number->digits-test
  (expecting "converts number to vector of digits"
    (expect [1 1] (#'number->digits 11 ))))
