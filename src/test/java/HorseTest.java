import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void nullNameIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,  () -> new Horse(null, 1, 5));
    }
    @Test
    public void nameCannotBeNullMessage(){
        try {
            new Horse(null, 1, 5);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    public void blankNameException(String name){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 5));
        assertEquals("Name cannot be blank.", e.getMessage());
    }
    @Test
    public void speedCannotBeNegativeMessage(){
        try {
            new Horse("Буцефал", -1, 5);
        }catch (IllegalArgumentException e){
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }
    @Test
    public void distanceCannotBeNegativeMessage(){
        try {
            new Horse("Буцефал", 1, -5);
        }catch (IllegalArgumentException e){
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Буцефал", 1, 5);
        //assertEquals("Буцефал", horse.getName());
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        //String nameValue = (String) name.get(horse);
        //name.set(horse, "asd");
        String nameValue = (String) name.get(horse);
        assertEquals("Буцефал", nameValue);
    }
    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Буцефал", 1, 5);
        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        double speedValue = (double) speed.get(horse);
        assertEquals(1, speedValue);
    }
    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Буцефал", 1, 5);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        double distanceValue = (double) distance.get(horse);
        assertEquals(5, distanceValue);
    }
    @Test
    public void zeroDistanceByDefault(){
        Horse horse = new Horse("Буцефал", 1);
        assertEquals(0, horse.getDistance());
    }
    @Test
    public void moveCallGetRandomWithParams(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Буцефал", 1, 5).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            //mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
            //mockedStatic.verify(() -> Horse.getRandomDouble(eq(0.2), anyDouble()));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.8, 1.0})
    public void move(double random){
       try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class);) {
           Horse horse = new Horse("Буцефал", 1, 5);
           horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
           horse.move();
           assertEquals(5 + 1 * random, horse.getDistance());
       }
    }
}
