package iview;

public class Equals {
    static final class Key<T> {
        private int hash;
        private final Long id;
        private final Class<T> cl;

        public Key(Long id, Class<T> cl) {
            this.id = id;
            this.cl = cl;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Key key = (Key) obj;

            if (!id.equals(key.id)) {
                return false;
            }
            if (!key.cl.isAssignableFrom(cl)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = id.hashCode() * 31;
                hash = hash + 31 * cl.hashCode();
            }
            return hash;
        }
    }
}
