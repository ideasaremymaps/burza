# burza
Burza (eng. Employment office) gathers simple statistics in related to the number of uneployed people in Croatia and publishes it on Twitter and Facebook (using GWT)

Project started: July 2014

The goal of this project is to have a daily statistics on the fluctuation of the unemployed people in the Republic of Croatia. 
The code parses the number of unemployed people in Croatia from the page of Croatian Employment Office (https://burzarada.hzz.hr/ ) 
every day at 8 o'clock A.M. and publishes it on the two following pages: 
  - https://twitter.com/burza_rada
  - https://www.facebook.com/Nezaposleni-320061638169077/

The message format is:
Translated:
Number of unemployed in Croatia: [total number] (difference from the last measurement)

Example: 
Broj nezaposlenih u Hrvatskoj: 157.263 (+330)

Currently the page is run from the following cloud:
https://console.cloud.google.com/home/dashboard?pli=1&project=nezaposlenost1 

Future features: 
  - automated monthly graphs of unemployed people
  - automatic analysis of fluctuations through time
