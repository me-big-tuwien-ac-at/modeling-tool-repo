<h1 align="center">GenericWebPortal</h1>
A generic web portal to access records from systematic mapping studies

## How to's

### Running the server through the command line

```commandline
python manage.py runserver
```

### How to prefill the database

The command below pre-populates the database the data contained within the file, provided in the placeholder 
``<fixturename>``. Execute the command from the content-root.
```commandline
python manage.py loaddata <fixturename>
```

In the case of the Modeling Tool Survey:
```commandline
python manage.py loaddata all_data.json
```

# TODO's ✅

## Data
* Create script that generates the fixture containing all the modeling tool data
* Prefill the database with data

## Homepage
### Main body
* Fix image example CSS
* Add option to zoom in on pictures

### Table
* Display all programming languages, modeling languages etc. as filtering option
* Fix option to download CSV and JSON
  * Fix the style of the buttons
* Add option to hide columns
* Fix functionality of hiding the column options
* Fix the entry fields of the column options

## Edit Modeling Tool Page
* Fix the CSS
* Implement Email functionality

<h1 style="color: #32DE84;">Done</h1>

## Overall
* Change font

## Homepage
### Header
* Remove the hover animation over the pencil icon within the "Suggest modeling tool edit"-button
* Adapt the "suggest new modeling tool" and "edit modeling" to phone and web mode
