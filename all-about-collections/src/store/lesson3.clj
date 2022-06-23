(ns store.lesson3
  (:require [store.db :as s.db]))

(println (group-by :user (s.db/all-orders)))

(defn my-grouping
  [element]
  (println "element" element)
  (:user element))

(println (keys (group-by my-grouping (s.db/all-orders))))
(println (map count (vals (group-by my-grouping (s.db/all-orders)))))


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
   :total-price-per-user (total-price orders)})

(->> (s.db/all-orders)
     (group-by :user)
     (map counts-total-of-orders-per-user)
     println)