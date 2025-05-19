## 📅 Leap Year Calculator

This module contains a simple utility class `LeapYearCalculator` located in the `org.leapyear` package. It provides functionality to determine whether a given year is a leap year according to the rules of the Gregorian calendar.

### 🔍 Functionality

- **Method:** `isLeapYear(int year)`
- **Returns:** `true` if the year is a leap year, `false` otherwise.
- **Validation:** Accepts only years in the range 1 to 9999 (inclusive). Any input outside this range returns `false`.

### 🧠 Leap Year Rules

A year is a leap year if:
- It is divisible by 4 **and**
  - Not divisible by 100, **unless**
  - It is also divisible by 400.

### ✅ Example Usage

```java
LeapYearCalculator.isLeapYear(2024); // returns true
LeapYearCalculator.isLeapYear(1900); // returns false
LeapYearCalculator.isLeapYear(2000); // returns true
LeapYearCalculator.isLeapYear(2023); // returns false
