(ns storage.discount)

(defn more-expensive-than-100?
  "Checks if prices applies for a discount"
  [original-price] (> original-price 100))

(defn discounted-price
  "Applies a 10% discount to the original price"
  [will-apply? price]
  (if (will-apply? price)
    (let [discount-rate 0.10,
          discount (* price discount-rate)]

      (- price discount))
    price))

(println (discounted-price more-expensive-than-100? 1000))
(println (discounted-price more-expensive-than-100? 100))