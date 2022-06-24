(ns mutability-with-atoms.collections
  (:use [clojure pprint]))

(defn test-vector
  []
  (let [g-queue [111 222]]
    (println g-queue)
    (println (conj g-queue 333))
    (println (pop g-queue))))

(println ">> Testing vector")
(test-vector)

(defn test-linked-list
  []
  (let [g-queue '(111 222)]
    (println g-queue)
    (println (conj g-queue 333))
    (println (pop g-queue))))

(println ">> Testing linked list")
(test-linked-list)

(defn test-set
  []
  (let [g-queue #{111 222}]
    (println g-queue)
    (println (conj g-queue 333))
    ;(println (pop g-queue))
    ))

(println ">> Testing set")
(test-set)

(defn test-queue
  []
  (let [g-queue (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println (seq g-queue))
    (println (seq (conj g-queue "333")))
    (println (seq (pop g-queue)))
    (println (seq (peek g-queue)))
    (pprint g-queue)
    ))

(println ">> Testing set")
(test-queue)