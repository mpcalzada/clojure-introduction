(ns mutability-with-atoms.lesson3
  (:use [clojure pprint])
  (:require
    [mutability-with-atoms.model :as h.model]
    [mutability-with-atoms.logic :as h.logic]))

(def my-name "william")
(def my-name "redefined")
(def my-name 12345)

(let [my-name "william"]
  (println my-name)
  (let [my-name "daniela"]
    (println my-name)))

(def hospital-grey (atom {}))

(defn test-atom
  []
  (let [hospital-grey (atom {:g-queue h.model/empty-queue})]
    (pprint hospital-grey)
    (pprint (deref hospital-grey))
    (pprint @hospital-grey)

    (assoc @hospital-grey :lab-1 h.model/empty-queue)
    (pprint @hospital-grey)

    (swap! hospital-grey assoc :lab-1 h.model/empty-queue)
    (pprint @hospital-grey)

    (swap! hospital-grey assoc :lab-2 h.model/empty-queue)
    (pprint @hospital-grey)

    (swap! hospital-grey update :lab-1 conj "111")
    (pprint @hospital-grey)
    ))

;(test-atom)


(defn terrible-arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at :g-queue person)
  (println "after inserting " person))

(defn not-so-terrible-arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at :g-queue person)
  (println "after inserting " person))

(defn simulates-a-day-in-parallel
  []
  (let [hospital (atom (h.model/new-hospital))]

    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "111"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "222"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "333"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "444"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "555"))))
    (.start (Thread. (fn [] (not-so-terrible-arrived-at! hospital "666"))))

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))

    )
  )

(simulates-a-day-in-parallel)