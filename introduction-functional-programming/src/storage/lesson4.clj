(ns storage.lesson4)

(def prices [30 700 1000])

(println (get prices 4 0))

(println (conj [5] prices))

(println (update prices 1 dec))

(defn applies-discount?
  "Checks if prices applies for a discount"
  [original-price] (> original-price 100))

(defn discounted-price
  "Applies a 10% discount to the original price"
  [price]
  (if (applies-discount? price)
    (let [discount-rate 0.10,
          discount (* price discount-rate)]

      (- price discount))
    price))

(println (map discounted-price prices))


(println (range 10))

(println (filter even? (range 10)))


(println (filter applies-discount? prices))
(println (map discounted-price (filter applies-discount? prices)))


(defn my-sum
  [val1 val2]
  (println "Adding " val1 val2)
  (+ val1 val2))


(println "My prices " (reduce my-sum prices))
(println "My ranges " (reduce my-sum 10 (range 5)))


(println (* 10 (+ 1 2 3 4 5)))