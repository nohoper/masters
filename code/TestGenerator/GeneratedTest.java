public class GeneratedTest {
private /*@ spec_public @*/ static int today = 0;
private /*@ spec_public @*/ static int ext_x = 0;
private /*@ spec_public @*/ static int bsh = 0;
private /*@ spec_public @*/ static int ext_sum = 0;

/*@ ensures ext_sum >=0 && ext_sum < 100000;
  @*/
public static void step1() {
    ext_sum = 48290;
     today = 0;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step2() {
    ext_x = 2063;

}

public static void step3() {
    ext_sum -= ext_x;
     today += ext_x;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step4() {
    ext_x = 21059;

}

public static void step5() {
    ext_sum -= ext_x;
     today += ext_x;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step6() {
    ext_x = 657;

}

public static void step7() {
    ext_sum -= ext_x;
     today += ext_x;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step8() {
    ext_x = 15296;

}

public static void step9() {
    ext_sum -= ext_x;
     today += ext_x;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step10() {
    ext_x = 3172;

}

public static void step11() {
    ext_sum -= ext_x;
     today += ext_x;

}

/*@ ensures ext_x >= 1000 && ext_x <= 15000;
  @*/
public static void step12() {
    ext_x = 7783;

}

public static void step13() {

}



public static void main(String[] args) {
    step1();
    step2();
    step3();
    step4();
    step5();
    step6();
    step7();
    step8();
    step9();
    step10();
    step11();
    step12();
    step13();
}

}