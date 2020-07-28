This is a simple android application that stores TimeCards and helps people keep track of the time
that they spend at work. All the user's data is stored in the cloud for persistence across devices.
The goal here is to create a simple solution to the inherant forgetfulness of us all.

The application is currently being worked on and has many bugs to address.
If you find bugs or problems please let me know.

TODO: 
    - FIx Loggin prompts for wrong password and nonexisting email!!!!
    - implement forgot password helper with UI

Current Bug List:
    - The register screen takes way too long to load and skips 47 frames
    - If i delete the user on firebase the app will still think that they are validated
        and starts at the main menu

Features to finish:
    -Animate the logging button on the main screen.
    -Add smooth transitions between pages
    -Add Logging in with Facebook and Google


Future Features:
    -ML for auto clock in using device location and past login data
    -Notes section under TimeEntries
    -TimeCard structure user settings
    -AutoEmail TimeCards at the end of the pay period
    -Calendar under history section for easier TimeEntry Searching
    -User Settings section(Urgent)
    -Update all of the findViewById() calls to DataBinding


Notes 7/3/2020:

There is a problem when the user clocks in, the activity is destroyted and we attempt to retrieve that user data.

In the MainClockInFrag there is a TODO that im not sure if needs to be handled or not.

Notes 7/7/2020

There is a bug in the state persistence creating a false clocked in timecard when clocked out.

Need to check that clock state is saved to preferences
!!!!Current problem!! Sign In button isnt activating once the form is filled-in.!!!!!

Notes 7/8/2020:

When logging in as a new user UserDataSourceManager.populateRoom() throws NullPointerException line 42 of UserDataSourceManger.

The current login logic neesd to change to trigger the loading sign while the task is completing and then update once the task is complete.

7/11/2020:
The app os throwing NPE when new user exits before room has initiated.