This is a simple android application that stores TimeCards and helps people keep track of the time
that they spend at work. All the user's data is stored in the cloud for persistence across devices.
The goal here is to create a simple solution to the inherant forgetfulness of us all.

The application is currently being worked on and has many bugs to address.
If you find bugs or problems please let me know.

TODO: URGENT!!
    - Remove the first and last name entrances when registering a new user in RegisterUserFragment

Current Bug List:
    - App currently closes when a login attempt is unsuccessful.
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
