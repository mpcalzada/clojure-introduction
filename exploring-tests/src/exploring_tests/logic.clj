(ns exploring-tests.logic
  (:require [exploring-tests.model :as h.model]
            [schema.core :as s]))

(defn fits-in-queue?
  [hospital department]
  (some-> hospital
          department
          count
          (< 5)))

(defn fits-in-queue?
  [hospital department]
  (when-let [queue (get hospital department)]
    (-> queue
        count
        (< 5))
    ))

(defn arrived-at
  [hospital department patient]
  (if (fits-in-queue? hospital department)
    (update hospital department conj patient)
    (throw (ex-info "This department is full or doesn't exists" {:patient patient :type :impossible-to-add-patient-to-the-queue})))
  )

#_(defn- tries-to-add-to-the-queue
  [hospital department patient]
  (if (fits-in-queue? hospital department)
    (update hospital department conj patient)))

#_(defn arrived-at
  [hospital department patient]
  (if-let [new-hospital (tries-to-add-to-the-queue hospital department patient)]
    {:hospital new-hospital, :result :success}
    {:hospital hospital, :result :impossible-to-add-patient-to-the-queue}))

(s/defn was-attended-to :- h.model/Hospital
  [hospital :- h.model/Hospital
   department :- s/Keyword]
  (update hospital department pop))

(s/defn next-patient :- h.model/PatientID
  [hospital :- h.model/Hospital
   department :- s/Keyword]
  (-> hospital
      department
      peek))

(s/defn transfer :- h.model/Hospital
  [hospital :- h.model/Hospital
   from :- s/Keyword
   to :- s/Keyword]
  (let [patient (next-patient hospital from)]
    (-> hospital
        (was-attended-to from)
        (arrived-at to patient))))