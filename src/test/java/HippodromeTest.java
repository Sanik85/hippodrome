import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

public class HippodromeTest {
    @Test
    public void nullNameIllegalArgumentException(){
        //List<Horse> horses =  Arrays.asList(new Horse("ass", 4,6));
        //assertThrows(IllegalArgumentException.class,  () -> new Hippodrome( horses));
        assertThrows(IllegalArgumentException.class,  () -> new Hippodrome( new ArrayList<>()));
    }
    @Test
    public void horseCannotBeNullMessage(){
        List<Horse> horses = isNull();
        try {
            new Hippodrome(horses);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }
    @Test
    public void horseCannotBeEmptyException(){
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,  () -> new Hippodrome(horses));
    }
    @Test
    public void horseCannotBeEmptyMessage(){
        List<Horse> horses = new ArrayList<>();
        try {
            new Hippodrome(horses);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }
    @Test
    public void getHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, i + 1, i + 1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        //assertSame(horses, hippodrome.getHorses());
        assertEquals(horses, hippodrome.getHorses());
    }
    @Test
    public void move(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            //verify(horse, times(2)).move();//колличество раз вызова метода move
            //verify(horse, atLeast(2)).move();//вызов метода move хотя бы 2 раза
            verify(horse).move();
            //verify(horse, atMostOnce()).move();//хотя бы 1 раз
        }
    }
    @Test
    public void getWinner(){
        Horse horse1 = new Horse("Буцефал", 2.4, 2);
        Horse horse2 = new Horse("Туз Пик", 2.5, 3);
        Horse horse3 = new Horse("Зефир", 2.6, 4);
        Horse horse4 = new Horse("Пожар", 2.7, 5);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse4, hippodrome.getWinner());

    }



}
