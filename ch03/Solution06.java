import java.util.*;


public class Solution06 {

    final static int ANIMAL_DOG = 0;
    final static int ANIMAL_CAT = 1;

    private static class Animal {
        public int type;
        public int index;

        public Animal(int t) {
            type = t;
            // Index must be set internally.
        }

        public String Type() {
            return (type == ANIMAL_DOG ? "Dog" : "Cat");
        }
    }

    private static class AnimalQueue {
        private LinkedList<Animal> dogs = new LinkedList<Animal>();
        private LinkedList<Animal> cats = new LinkedList<Animal>();
        private int index = 0;  // Mark the order that the animals were put in the shelter.

        public void enqueue(Animal a) {
            // Set the order of the animal
            a.index = index;
            ++index;

            // Put in the right list.
            if (a.type == ANIMAL_DOG) {
                dogs.addLast(a);
            } else {
                cats.addLast(a);
            }
        }

        public Animal dequeueAny() throws Exception {
            if (dogs.isEmpty() && cats.isEmpty()) {
                throw new Exception("The shelter is already empty.");
            }

            // If one queue is empty, return the first animal from the other
            // queue.
            if (dogs.isEmpty()) {
                return cats.poll();
            } else if (cats.isEmpty()) {
                return dogs.poll();
            }

            // If both queues are not empty, we need to check which animal
            // arrived earlier.
            Animal dog = dogs.peek();
            Animal cat = cats.peek();
            if (dog.index < cat.index) {
                return dogs.poll();
            } else {
                return cats.poll();
            }
        }

        public Animal dequeueDog() {
            return dogs.poll();
        }

        public Animal dequeueCat() {
            return cats.poll();
        }
    }

    public static void main(String []args) throws Exception {
        boolean pass = true;

        {
            AnimalQueue aq = new AnimalQueue();
            aq.enqueue(new Animal(ANIMAL_DOG));     // index: 0
            aq.enqueue(new Animal(ANIMAL_CAT));     // index: 1
            aq.enqueue(new Animal(ANIMAL_DOG));     // index: 2
            aq.enqueue(new Animal(ANIMAL_DOG));     // index: 3
            aq.enqueue(new Animal(ANIMAL_CAT));     // index: 4

            Animal a = aq.dequeueAny();
            System.out.println("Animal: " + a.Type() + ", " + a.index);
            a = aq.dequeueAny();
            System.out.println("Animal: " + a.Type() + ", " + a.index);
            a = aq.dequeueCat();
            System.out.println("Animal: " + a.Type() + ", " + a.index);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
