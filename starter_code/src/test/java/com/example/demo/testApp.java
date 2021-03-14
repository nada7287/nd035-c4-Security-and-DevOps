package com.example.demo;

import com.example.demo.controllers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CartControllerTest.class, ItemControllerTest.class, OrderControllerTest.class, UserControllerTest.class})
class AppTest {

}
