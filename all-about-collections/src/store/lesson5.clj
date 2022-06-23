(ns store.lesson5
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))

(defn spent-a-lot?
  [user-info]
  (> (:total-price user-info) 500))

(let [orders (s.db/all-orders)
      summary (s.logic/summary-per-user (s.db/all-orders))]
  (println "keep" (keep spent-a-lot? summary))
  (->> summary
       (keep spent-a-lot?)
       (filter))
  )

(let [sequence (range 10000000)]
  (println (take 2 sequence)))

(defn filter1
  [value]
  (println "filter1" value)
  value)

(defn filter2
  [value]
  (println "filter2" value)
  value)

;(println (map filter2 (map filter1 (range 10))))

(->> '(1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9)
     (map filter1)
     (map filter2)
     (println))