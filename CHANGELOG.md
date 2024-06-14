# World Clock Change Log

## 1.8.0 (2024/06/14)
* integrate the new IDE [Exception Analyzer](https://plugins.jetbrains.com/docs/marketplace/exception-analyzer.html). This is an easy way to report plugin exceptions from IntelliJ Platform-based products to plugin developers right on JetBrains Marketplace, instead of opening an issue on the plugin's GitHub repository.

## 1.7.2 (2024/03/04)
* minor performance optimization: reduce threads spawning by using IDE thread pool and scheduler instead of starting new threads for scheduled tasks.

## 1.7.1 (2024/02/21)
* Fix Canadian rounded flag when using a dark theme.
* Optimize many icons with recent version of SVGO.

## 1.7.0 (2024/01/05)
* Settings UI: the timezone choosers now integrate the **Speed Search** functionality, [similar to Speed Search for Tool Windows](https://www.jetbrains.com/help/idea/speed-search-in-the-tool-windows.html).

## 1.6.1 (2023/09/10)
* Fix rendering of `uz` (Asia/Samarkand, Asia/Tashkent) and `ve` (America/Caracas) flags in 2023.2 IDEs.
* Some code rework.

## 1.6.0 (2023/01/14)
* Internal: add TimeZone aliases support.
* Add some Indian cities.

## 1.5.1 (2022/11/26)
* Remove usage of deprecated JetBrains API, fix compatibility with future IDE releases.

## 1.5.0 (2022/10/08)
* Introduce flags themes. Select your theme in Settings.

## 1.4.0 (2022/08/12)
* Add an option to use 24-hour time format.

## 1.3.0 (2022/07/23)
* Add CST, EST, PST and UTC timezones.

## 1.2.0 (2022/05/07)
* Code rework.
* Add missing timezones.

## 1.1.0 (2021/10/17)
* First release.

## 1.0.0
* POC. Not published.
