(ns storage.lesson6)

(def clothes {:shirt {:quantity 10, :price 100}, :backpack {:quantity 4, :price 50}})

(defn prints-value-and-returns-15
  [value]
  (println "value: "value (class value)))

(defn prints-value-and-returns-15
  [[key value]]
  (println "value: "value "key: " key "with" (-> value :quantity)))

(map prints-value-and-returns-15 clothes)

(defn total-price-per-product
  [[key value]]
  (* (:quantity value) (:price value)))

(println (reduce + (map total-price-per-product clothes)))

(defn total-order-price
  [order]
  (reduce + (map total-price-per-product order)))

(println (total-order-price clothes))


; Tread first with ->
; Thread last with ->>
(defn total-order-price
  [order]
  (->> order
      (map total-price-per-product)
      (reduce +)))

(println (total-order-price clothes))


(def clothes {
              :shirt {:quantity 10, :price 100},
              :backpack {:quantity 4, :price 50}
              :keychain {:quantity 1, :price 0}})

(defn for-free?
  [item]
  (<= (-> item :price) 0))


(println (filter (fn [[_ value]] (for-free? value))  clothes))
(println (filter #(for-free? (second %)) clothes))

(defn paid?
  [item]
  (not (for-free? item)))

(println (filter #(paid? (second %)) clothes))


(def paid? (comp not for-free?))
(println (filter #(paid? (second %)) clothes))

