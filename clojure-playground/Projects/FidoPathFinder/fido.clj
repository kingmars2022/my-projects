;; -------------
;; This is the menu template for the Fido app. 
;; ------------

(ns fido
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io])
  (:require [food]))

;;-------------------
;; THE MENU FUNCTIONS
;; ------------------

;; Display the menu and ask the user for the option
(defn showMenu
  []
  (println "\n\n*** Let's Feed Fido ***")
  (println     "-----------------------\n")
  (println "1. Display list of map files")
  (println "2. Display a map for Fido")
  (println "3. Exit")
  (do 
    (print "\nEnter an option? ") 
    (flush) 
    (read-line)))

;; Wait for user input before proceeding (used for pauses)
(defn wait-key
  []
  (print "\nPress any key to continue ")
  (flush)
  (.read System/in)
  (read-line)
  (print (str (char 27) "[2J"))
  (flush))

;; Display all files in the current folder
(defn option1
  []
  (let [files (filter #(.endsWith (.getName %) ".txt") (file-seq (io/file ".")))]
    (println "\nMap List:")
    (doseq [file files]
      (println (str " * " (.getPath file))))
    (wait-key)))
     
;; Get the map specified by the user and start looking for the food
(defn option2
  []
  (print "\nPlease enter a file name => ") 
  (flush)
  (let [file-name (read-line)
        [exists, m] (food/read-map-file file-name)
        [found m'] (food/find-path m)]
    (if (not exists)
      (println (str "Oops: specified file " file-name " does not exist"))
      (do
        (println "\nThis is Fido's challenge:\n")
        (food/print-map m)
        (if
         (not (food/is-valid-map m)) (println "\nUnfortunately, this is not a valid food map for Fido\n")
         (do
           (if found
             (println "\nWoo Hoo - Fido found her food\n")
             (println "\nOh no - Fido could not find her food\n"))
           (food/print-map m')))))
    (wait-key)))


; If the menu selection is valid, call the relevant function to 
; process the selection
(defn processOption
  [option] 
  (if( = option "1")
     (option1)
     (if( = option "2")
        (option2)   
        (println "Invalid menu option"))))

; Display the menu and get a menu item selection. Process the
; selection and then loop again to get the next menu selection
(defn menu
  [] ; parm(s) can be provided here, if needed
  (let [option (str/trim (showMenu))]
    (if (= option "3")
      (println "\nGood Bye\n")
      (do 
         (processOption option)
         (recur )))))   

; ------------------------------
; Run the program.
(menu)
