(ns mutability-with-atoms.lesson5
  (:use [clojure pprint])
  (:require
    [mutability-with-atoms.model :as h.model]
    [mutability-with-atoms.logic :as h.logic]))

(defn arrived-at!
  [hospital person]
  (swap! hospital h.logic/arrived-at :g-queue person)
  (println "after inserting " person))

(defn transfer!
  [hospital from to]
  (swap! hospital h.logic/transfer from to))

(defn simulates-a-day
  []
  (let [hospital (atom (h.model/new-hospital))]
    (arrived-at! hospital "John")
    (arrived-at! hospital "Mary")
    (arrived-at! hospital "Daniela")
    (arrived-at! hospital "William")
    (pprint hospital)
    (transfer! hospital :g-queue :lab-1)
    (transfer! hospital :g-queue :lab-2)
    (transfer! hospital :g-queue :lab-2)
    (transfer! hospital :lab-2 :lab-3)
    (pprint hospital)

    ))

(simulates-a-day)