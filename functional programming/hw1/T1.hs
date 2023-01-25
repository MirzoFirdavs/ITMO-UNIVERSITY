module HW1.T1 
  ( Day (..)
  , afterDays
  , daysToParty
  , isWeekend
  , nextDay
  ) where

import Numeric.Natural

data Day = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday deriving (Show)

nextDay :: Day -> Day
nextDay Monday    = Tuesday
nextDay Tuesday   = Wednesday
nextDay Wednesday = Thursday
nextDay Thursday  = Friday
nextDay Friday    = Saturday
nextDay Saturday  = Sunday
nextDay Sunday    = Monday

afterDays :: Natural -> Day -> Day
afterDays n d = if n == 0 then d else afterDays (n - 1) (nextDay d)

isWeekend :: Day -> Bool
isWeekend Monday = False
isWeekend Tuesday = False
isWeekend Wednesday = False
isWeekend Thursday = False
isWeekend Friday = False
isWeekend Saturday = True
isWeekend Sunday = True

daysToParty :: Day -> Natural
daysToParty Friday = 0
daysToParty d = 1 + daysToParty (nextDay d)