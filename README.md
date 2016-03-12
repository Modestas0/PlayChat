# PlayChat
Chat web application written using Java and Play Framework

### Activator console
To start activator console application, run `activator` from root directory of the project.

### Compiling
In activator console type `compile`

### Running
In activator console type `run`

### Database
PlayChat uses MySQL database

You can import database and tables structure from file `playchat.sql` (placed in root directory)

To change MySQL connection parameters, edit `conf/application.conf` file. At the bottom of this file there are `default.url = "mysql://root:password@localhost/playchat"` which should be changed.

### Room for improvement
* All messages could not be loaded because it is slow (especially on mobile devices). Currently there are a limit of 50 messages. There could be incremental loading on page scroll up or on button press.
* Due to the way Play framework handles sessions, all session variables (timestamp of last request, user id and user name) is visible for the user (but not editable). Maybe this information should be stored in database or in memory and only some kind of identifier should be stored in session variables
