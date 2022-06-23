(ns store.logic)

(defn total-price-per-item
  [[_ details]]
  (* (get details :quantity 0) (get details :price-per-unit 0)))

(defn total-price-per-order
  [order]
  (->> order
       (map total-price-per-item)
       (reduce +)))

(defn total-price
  [orders]
  (->> orders
       (map :items)
       (map total-price-per-order)
       (reduce +))
  )
(defn counts-total-of-orders-per-user
  [[user orders]]
  {:user-id user,
   :total-of-orders (count orders)
   :total-price (total-price orders)})

(defn summary-per-user
  [orders]
  (->> orders
       (group-by :user)
       (map counts-total-of-orders-per-user)))