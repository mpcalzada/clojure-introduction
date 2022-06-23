(ns store.lesson4
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))

(let [orders (s.db/all-orders)
      summary (s.logic/summary-per-user (s.db/all-orders))]
  (println "Reverse sorted " (reverse (sort-by :total-price summary)))
  (->> summary
       (sort-by :total-price)
       (println))
  (println summary)
  (println (get-in orders [0 :items :backpack :quantity])))


(defn summary-per-user-sorted
  [orders]
  (->> orders
       s.logic/summary-per-user
       (sort-by :total-price))
  )

(let [orders (s.db/all-orders)
      summary (summary-per-user-sorted orders)]
  (println summary)
  ;gives the first user
  (println (first summary))
  ;gives all the users but the first
  (println (rest summary))
  ;gives all the users but the first
  (println (next summary))
  ;gives the type of the element
  (println (class summary))
  ;retrieves the desired element
  (println (nth summary 1))
  ;tries to get the first element (Only works for arrays) Not working on this.
  (println (get summary 1))
  ;retrieves N elements
  (println (take 2 summary))
  )

(let [orders (s.db/all-orders)
      summary (summary-per-user-sorted orders)]
  (println "Filtering")
  (println (->> summary
                (filter #(> (:total-price %) 500))
                (empty?)
                (not)))
  (println (->> summary
                (some #(> (:total-price %) 500))))
  )