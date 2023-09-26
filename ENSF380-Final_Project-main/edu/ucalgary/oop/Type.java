/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

enum Type {
    NOCTURNAL {

        public String toString() {
            return "Nocturnal";
        }

    },
    DIURNAL {
        public String toString() {
            return "Diurnal";
        }
    },
    CREPUSCULAR {
        public String toString() {
            return "Crepuscular";
        }
    }
}
