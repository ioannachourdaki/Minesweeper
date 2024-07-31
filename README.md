# Minesweeper

This project was developed for the Multimedia Technology course. It leverages **Java** to implement all core functionalities and **JavaFX** for the Graphical User Interface (GUI).

## Features

There are 2 levels of difficulty, each with specific features:
| Difficulty Level | Size     | Mines    | Available time (seconds) | Super-mine |
|------------------|----------|----------|--------------------------|------------|
| 1                | 9 x 9    | [9 - 11] | [120 - 180]              | No         |
| 2                | 16 x 16  | [35 - 45]| [240 - 360]              | Yes        |



Στην παρούσα εξαμηνιαία εργασία ζητείται η υλοποίηση μιας παραλλαγής του κλασσικού παιχνιδιού «Ναρκαλιευτής», όπου ο χρήστης θα παίζει με αντίπαλο τον υπολογιστή.

 ## Σχεδιασμός Υλοποίησης 

- **Class Board:** Το ταμπλό του παιχνιδιού υλοποιείται από την κλάση board, η οποία αρχικοποιεί τα χαρακτηριστικά 
του παιχνιδιού (επίπεδο - level, αριθμός ναρκών - mines, συνολικός διαθέσιμος χρόνος – time, ύπαρξη 
υπερνάρκης – supermineOn) και δημιουργεί κατάλληλα το ταμπλό (Tile [][] hidden), τοποθετώντας σε 
τυχαίες θέσεις τις νάρκες. Το ταμπλό αποτελείται από τετράγωνα, το καθένα από τα οποία είναι είτε 
απλό τετράγωνο – Tile, είτε νάρκη – Mine, είτε υπερνάρκη – Supermine. Επιπλέον, υλοποιούνται εδώ 
η νίκη (method win()) και η ήττα (method lose()) του παιχνιδιού. Εφόσον ο παίχτης αποκαλύψει όλα 
τα απλά τετράγωνα, αποκαλύπτονται με μπλε φόντο τα τετράγωνα με νάρκες και καταχωρείται ως 
νικητής ο παίχτης. Αντίθετα, αν πατηθεί τετράγωνο με νάρκη αποκαλύπτονται με γκρι φόντο τα 
τετράγωνα με νάρκες και καταχωρείται ως νικητής ο υπολογιστής.

- **Class Tile:** Η κλάση Tile υλοποιεί κάθε απλό τετράγωνο του ταμπλό, δηλαδή τετράγωνο το οποίο δεν είναι νάρκη 
ή υπερνάρκη. Περιέχει έναν αριθμό (number), που υποδεικνύει το πλήθος των ναρκών που βρίσκονται 
γειτονικά από αυτό. 

- **Class Mine:** Η κλάση Mine υλοποιεί κάθε νάρκη. Στην περίπτωση που ο παίχτης προσπαθήσει να αποκαλύψει 
τετράγωνο με νάρκη, το παιχνίδι σταματά και ο παίχτης χάνει. Κατά την εισαγωγή μίας νάρκης στο 
ταμπλό του παιχνιδιού, η νάρκη ενημερώνει όλους τους γείτονές της για την ύπαρξή της. Δηλαδή, 
ενημερώνεται το πεδίο number κάθε αντικειμένου Tile.

- **Class Supermine:** Η κλάση Supermine υλοποιεί την υπερνάρκη. Διατηρώντας όλα τα χαρακτηριστικά της κλάσης Mine, 
προσθέτει μία επιπλέον ιδιότητα στην σημείωσή της με σημαία. Αν κατά τη διάρκεια των πρώτων 4ων
γύρων του παιχνιδιού, αν ο παίκτης σημειώσει την υπερνάρκη με σημαία, τότε αποκαλύπτονται τα όλα 
τετράγωνα που βρίσκονται στην ίδια στήλη και στην ίδια γραμμή με αυτήν. Εφόσον προκύψει 
αποκάλυψη νάρκης σε αυτή τη διαδικασία, η ίδια απενεργοποιείται.

- **Flag:** Κάθε τετράγωνο, ανεξαρτήτως είδους, μπορεί να σημειωθεί με μία σημαία (flag), αν το πλήθος των 
σημαιών που έχουν χρησιμοποιηθεί δεν ξεπερνά το συνολικό πλήθος των ναρκών. Εξαίρεση αποτελεί
η περίπτωση σημείωσης με σημαία της υπερνάρκης κατά τη διάρκεια των πρώτων 4ων γύρων του  παιχνιδιού. 
Σε αυτή την περίπτωση, αν κατά την αποκάλυψη τετραγώνων προκύψει η αποκάλυψη 
νάρκης/ναρκών, τότε το συνολικό πλήθος σημαιών που μπορεί να χρησιμοποιηθεί είναι ίσο με το 
πλήθος ναρκών που δεν έχουν αποκαλυφθεί από αυτή τη διαδικασία, δηλαδή: 
*#flags = #mines – (supermine + #mines_revealed)*. Κάθε σημείωση τετραγώνου με σημαία μπορεί να 
αναιρεθεί (unflag).

- **Class PopupWindow:** Η κλάση PopupWindow υλοποιεί την ουρά τελευταίων παιχνιδιών που χρησιμοποιείται από την 
επιλογή “Round” του Menu Bar. Μπορεί να δεχθεί έως 5 strings (περιγραφές) και στην προσθήκη ενός 6
ου στοιχείου εφαρμόζει μέθοδο FIFO, διατηρώντας έτσι τις περιγραφές των πιο πρόσφατων
παιχνιδιών.

- **Class Exceptions:** Η κλάση Exceptions περιλαμβάνει δύο μεθόδους: checkInput και checkFile. Η πρώτη ελέγχει αν οι 
τιμές που έχουν δοθεί για την αρχικοποίηση ενός παιχνιδιού είναι εντός των περιορισμών που έχουν 
τεθεί από την εκφώνηση της εργασίας και αν αυτό δεν ισχύει κάνει throw την εξαίρεση 
InvalidValueException. Η δεύτερη ελέγχει αν έχουν δοθεί όλες οι απαιτούμενες τιμές από το αρχείο 
αρχικοποίησης, διαφορετικά κάνει throw την εξαίρεση InvalidValueException.

## Graphical User Interface (JavaFX)

Η γραφική διεπαφή υλοποιείται στην **κλάση Main**. Ορίζεται εδώ το Menu Bar, το οποίο περιλαμβάνει 
τις επιλογές “Application” > “Create”, “Load”, “Start”, “Exit” και “Details” > “Round”, “Solution”. 

- **“Create”:** Εμφάνιση popup παραθύρου με πεδία προς συμπλήρωση (SCENARIO-ID, LEVEL, NUMBER
OF MINES, SUPERMINE, MAX TIME). Δημιουργεί ένα αρχείο αρχικοποίησης ταμπλό παιχνιδιού 
“SCENARIO-ID.txt” και το αποθηκεύει στον φάκελο medialab.

- **“Load”:** Εμφάνιση popup παραθύρου με πεδίο προς συμπλήρωση (SCENARIO-ID). Ελέγχει αν το 
αρχείο “SCENARIO-ID.txt” υπάρχει και αν η περιγραφή και η τιμές που περιλαμβάνει είναι έγκυρες. 
Αν ναι, τότε αρχικοποιεί ένα καινούργιο board με βάση αυτές τις τιμές και έπειτα, δημιουργεί και
αποθηκεύει στον φάκελο medialab το αρχείο “mines.txt” που περιλαμβάνει τις τρέχουσες θέσεις των 
ναρκών. Διαφορετικά, εμφανίζεται ένα popup παράθυρο με κατάλληλο μήνυμα σφάλματος/εξαίρεσης.

- **“Start”:** Δημιουργεί γραφικές περιγραφές του πλήθους ναρκών σύμφωνα με την περιγραφή που έγινε 
load, του πλήθους σημαιών που χρησιμοποιούνται και του διαθέσιμου χρόνου παιχνιδιού. Το ρολόι 
μετρά αντίστροφα ξεκινώντας από τα συνολικά διαθέσιμα δευτερόλεπτα που έχουν οριστεί από το load.
Επιπλέον, το “Start” ρυθμίζει τις διαστάσεις παραθύρου παιχνιδιού ανάλογα με το μέγεθος του ταμπλό
και δημιουργεί τα γραφικά για τα κλειστά τετράγωνα.
Πρέπει πάντα να γίνεται πρώτα επιλογή παιχνιδιού από το “Load” και έπειτα επιλογή του “Start”, ώστε
να γίνει σωστή αρχικοποίηση του παιχνιδιού, ακόμα και αν πρόκειται να επιλεχθεί το ίδιο παιχνίδι. 

- **“Exit”:** Το παιχνίδι τερματίζει και το παράθυρο παιχνιδιού κλείνει.

- **“Round”:** Εμφάνιση popup παραθύρου με τα χαρακτηριστικά των τελευταίων 5 ολοκληρωμένων 
παιχνιδιών που έχουν προστεθεί στην ουρά του αντικειμένου της κλάσης PopupWindow. 

- **“Solution”:** Τερματισμός τρέχοντος παιχνιδιού, αποκάλυψη θέσεων ναρκών και καταχώρηση του 
υπολογιστή ως νικητή.

Επιπλέον, η κλάση Main υλοποιεί τα Events χρήσης ποντικιού. 
- Με **αριστερό κλικ** πάνω σε κάποιο 
τετράγωνο γίνεται η αποκάλυψη του τετραγώνου
- Mε **δεξί κλικ** προστίθεται ή αναιρείται σημαία

Αν το παιχνίδι τερματίσει λόγω νίκης ή ήττας του παίχτη τα κλικ του ποντικιού *απενεργοποιούνται*.

Τέλος, ελέγχει αν ο παίχτης έχει κερδίσει και αν αυτό ισχύει εμφανίζει κατάλληλο μήνυμα νίκης
(“Congratulations!”) σε popup window.
