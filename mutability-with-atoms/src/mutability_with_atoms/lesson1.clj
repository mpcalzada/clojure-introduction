(ns mutability-with-atoms.lesson1
  (:use [clojure pprint])
  (:require
    [mutability-with-atoms.model :as h.model]
    [mutability-with-atoms.logic :as h.logic]))


(defn simulates-a-day
  []
  (def hospital (h.model/new-hospital))
  (def hospital (h.logic/arrived-at hospital :g-queue "111"))
  (def hospital (h.logic/arrived-at hospital :g-queue "222"))
  (def hospital (h.logic/arrived-at hospital :g-queue "333"))

  (def hospital (h.logic/arrived-at hospital :lab-1 "444"))
  (def hospital (h.logic/arrived-at hospital :lab-3 "555"))

  (pprint hospital)

  (def hospital (h.logic/was-attended-to hospital :lab-1))
  (def hospital (h.logic/was-attended-to hospital :g-queue))

  (def hospital (h.logic/arrived-at hospital :g-queue "666"))
  (def hospital (h.logic/arrived-at hospital :g-queue "777"))
  (def hospital (h.logic/arrived-at hospital :g-queue "888"))
  (pprint hospital)
  (def hospital (h.logic/arrived-at hospital :g-queue "999"))
  (pprint hospital))



;(simulates-a-day)

(defn terrible-arrived-at
  [person]
  (def hospital (h.logic/arrived-at-with-pauses hospital :g-queue person))
  (println "after inserting " person))

(defn simulates-a-day-in-parallel
  []
  (def hospital (h.model/new-hospital))

  (.start (Thread. (fn [] (terrible-arrived-at "111"))))
  (.start (Thread. (fn [] (terrible-arrived-at "222"))))
  (.start (Thread. (fn [] (terrible-arrived-at "333"))))
  (.start (Thread. (fn [] (terrible-arrived-at "444"))))
  (.start (Thread. (fn [] (terrible-arrived-at "555"))))
  (.start (Thread. (fn [] (terrible-arrived-at "666"))))

  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital))))

  )

(simulates-a-day-in-parallel)