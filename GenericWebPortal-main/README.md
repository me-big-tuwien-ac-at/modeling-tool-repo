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

## Homepage
### Main body
* Add hover effect for light + dark theme for the information titles 
```css
.information-title {
  display: flex;
  gap: 20px;
  margin-bottom: 1rem;
  border-bottom: solid #a2a9b1 1px;
  border-radius: 11px 11px 0 0;
  padding: 0.5rem 1rem;
}
.information-title:hover:not(:last-child) {
  background: #D3D3D3;
  transition: .4s;
}
```

### Table
* Adjust background of table cells when switching the theme
* Display all programming languages, modeling languages etc. as filtering option
* Add option to hide columns
* Fix functionality of hiding the column options
* Fix the entry fields of the column options
* Sort columns when clicking on a ``th``-tag

## Create Modeling Tool Page
* Implement forms
* Fix the CSS
* Implement Email functionality

## Edit Modeling Tool Page

<h1 style="color: #32DE84;">Done</h1>

## Data
* Create script that generates the fixture containing all the modeling tool data
* Prefill the database with data

## Overall
* Change font
* Move phone/web adjusting properties into a single file, which is then imported to each js-file

## Homepage
### Main body
* Fix image example CSS
* Add option to zoom in on pictures

### Header
* Remove the hover animation over the pencil icon within the "Suggest modeling tool edit"-button
* Adapt the "suggest new modeling tool" and "edit modeling" to phone and web mode

### Table
* Fix option to download CSV and JSON
  * Fix the style of the buttons