(ns app.service)

(defn- pos-int-or-zero? [x]
  (or (pos-int? x) (= x 0)))

(defn- check-valid-values [values]
  "Throws ExceptionInfo if values vector is empty or vector elements are not positive integer or zero."
  (if (seq values)
    (when-not (every? pos-int-or-zero? values)
      (throw (ex-info "Invalid input, expected values vector elements to be positive int or zero." {:code 422})))
    (throw (ex-info "Values vector is empty." {:code 422}))))

(defn- number->digits [x]
  "Given a number, it returns a vector of digits.
  Eg: (number->digits 11) => [1 1]"
  (if (< x 10)
    [x]
    (conj (number->digits (quot x 10))
      (rem x 10))))

(defn compute [m]
  "Expects a map with {:address {:values [] }} keys,
  performs the sum of the elements in :value vector and returns a map with the sum of its digits.

  Eg: (compute {:address {:values [11 11]}}) => {:result 4}"
  (if-let [values (get-in m [:address :values])]
    (do
      (check-valid-values values)
      (->> (reduce + values)
        number->digits
        (reduce +)
        (hash-map :result)))
    (throw (ex-info "Invalid input" {:code 422}))))
