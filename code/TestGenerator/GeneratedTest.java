public class GeneratedTest {
private /*@ spec_public @*/ int today = 0;
private /*@ spec_public @*/ int ext_x = 0;
private /*@ spec_public @*/ int bsh = 0;
private /*@ spec_public @*/ int ext_sum = 0;
  //@ public invariant today <= 25000;

/*@ ensures  ext_sum >=0 && ext_sum < 100000;
  @*/
public void transition1() {
    ext_sum = 33177;
    today = 0;
}

/*@ ensures  ext_x >= 1000 && ext_x <= 15000;
  @*/
public void transition2() {
    ext_x = 13115;

}

public void transition3() {
    ext_sum -= ext_x;
    today += ext_x;

}

/*@ ensures  ext_x >= 1000 && ext_x <= 15000;
  @*/
public void transition4() {
    ext_x = 14485;
}

public void transition5() {
    ext_sum -= ext_x;
    today += ext_x;

}

/*@ ensures  ext_x >= 1000 && ext_x <= 15000;
  @*/
public void transition6() {
    ext_x = 4382;
}

public void transition7() {
    ext_sum -= ext_x;
    today += ext_x;
}

/*@ ensures  ext_x >= 1000 && ext_x <= 15000;
  @*/
public void transition8() {
    ext_x = 8513;
}

public static void main(String[] args) {
    GeneratedTest test = new GeneratedTest();
    test.transition1();
    test.transition2();
    test.transition3();
    test.transition4();
    test.transition5();
    test.transition6();
    test.transition7();
    test.transition8();
}

}