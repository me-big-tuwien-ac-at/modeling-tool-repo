/***********************************
 THEME
************************************/
const bodyElement = document.getElementsByTagName('body')[0];
// TODO (Optional): Dynamically add svg's, instead of switching between "display: none;"
const sunSvg = document.getElementsByClassName('bi-sun-fill')[0];
const moonSvg = document.getElementsByClassName('bi-moon-fill')[0];
const images = document.getElementsByClassName('image');
setImageTheme();
setInformationSectionTitleHover();

moonSvg.addEventListener('click', () => {
   setImageTheme();
   setInformationSectionTitleHover();
});

sunSvg.addEventListener('click', () => {
   setImageTheme();
   setInformationSectionTitleHover();
});

/**
 * Switches between the background of the information-title to dark or white, depending on the theme.
 */
function setInformationSectionTitleHover() {
    const theme = localStorage.getItem('theme');
    if (theme !== null) {
        const informationTitles = document.getElementsByClassName('information-title');
        if (theme === 'dark') {
            for (let i = 0; i < informationTitles.length; i++) {
                informationTitles[i].classList.add('dark-hover');
            }
        } else if (theme !== null && theme === 'light') {
            for (let i = 0; i < informationTitles.length; i++) {
                informationTitles[i].classList.remove('dark-hover');
            }
        }
    }
}

function setImageTheme() {
    for (let i = 0; i < images.length; i++) {
        if (
            bodyElement.style.backgroundColor === 'var(--light-theme)' ||
            bodyElement.style.backgroundColor === '#FFF' ||
            bodyElement.style.backgroundColor === '#FFFFFF' ||
            bodyElement.style.backgroundColor === ''
        ) {
            images[i].classList.add('bg-dark-light');
            images[i].classList.remove('bg-light-dark');
    } else {
            images[i].classList.add('bg-light-dark');
            images[i].classList.remove('bg-dark-light');
        }
    }
}

/***********************************
 COLLAPSIBLE/EXPANDABLE SECTIONS
************************************/
const informationSection = document.getElementById('information');
const informationSectionChevronRight = informationSection.getElementsByClassName('bi-chevron-right');
const informationSectionChevronDown = informationSection.getElementsByClassName('bi-chevron-down');

informationSectionChevronRight[0].style.display = 'none';
informationSectionChevronDown[1].style.display = 'none';
informationSectionChevronDown[2].style.display = 'none';

const informationDetails = document.getElementsByClassName('information-details');
informationDetails[1].style.display = 'none';
informationDetails[2].style.display = 'none';

// EventListeners for on-click events
const informationHeader = document.getElementsByClassName('information-title');

for (let i = 0; i < informationHeader.length; i++) {
    informationHeader[i].addEventListener('click', function () {
        const chevronDown = informationHeader[i].getElementsByClassName('bi-chevron-down')[0];
        const chevronRight = informationHeader[i].getElementsByClassName('bi-chevron-right')[0];
        if (chevronDown.style.display === 'none') {
            // informationHeader[i].nextElementSibling.style.display = 'block';
            informationHeader[i].nextElementSibling.style.display = null;
            chevronDown.style.display = 'block';
            chevronRight.style.display = 'none';
            galleryListeners();
        } else {
            informationHeader[i].nextElementSibling.style.display = 'none';
            chevronDown.style.display = 'none';
            chevronRight.style.display = 'block';
        }
    });
}

/***********************************
 ELEMENTS WHERE PROPERTIES ADAPT TO WEB/PHONE MODE
************************************/
phoneWebModeCssSetter();

window.addEventListener('resize', function(event){
    phoneWebModeCssSetter();
});

function phoneWebModeCssSetter() {
    const webElements = document.getElementsByClassName('web-block-mode');
    const phoneElements = document.getElementsByClassName('phone-block-mode');
    if (window.innerWidth <= 1400) {
        for (let i = 0; i < webElements.length && i < phoneElements.length; i++) {
            webElements[i].style.display = 'none';
            phoneElements[i].style.display = 'block';
        }
    } else {
        for (let i = 0; i < webElements.length && i < phoneElements.length; i++) {
            webElements[i].style.display = 'block';
            phoneElements[i].style.display = 'none';
        }
    }
}


/***********************************
 ZOOMING IN A PICTURE
************************************/
const modelingToolExamples = document.getElementsByClassName('modeling-tool-example');
const expandedExample = modelingToolExamples[0];

imgListener(expandedExample);

const galleryListeners = (event = 'click') => {
    for (let i = 0; i < modelingToolExamples.length; i++) {
        imgListener(modelingToolExamples[i]);
    }
}

function imgListener(modelingToolExample) {
    modelingToolExample.addEventListener('click', function () {
        const zoomedInElement = document.createElement('div');
        zoomedInElement.setAttribute('id', 'image-zoom-in');
        zoomedInElement.setAttribute('class', 'image-zoom-in');
        if (modelingToolExample.src.width > modelingToolExample.src.height) {
            zoomedInElement.innerHTML = `
          <span id="close">×</span>
          <div class="image-bg"></div>
          <img class="image-zoom" src="${modelingToolExample.src}" width="70%" alt="${modelingToolExample.alt}">
    `;
        } else {
            zoomedInElement.innerHTML = `
          <span id="close">×</span>
          <div class="image-bg"></div>
          <img class="image-zoom" src="${modelingToolExample.src}" height="70%" alt="${modelingToolExample.alt}">
    `;
        }

        bodyElement.appendChild(zoomedInElement);
        closeZoomIn('close', 'image-bg');
    });
}

const closeZoomIn = (xButton, bg, event = 'click') => {
  document.getElementById(xButton).addEventListener(event, function() {
      document.getElementById('image-zoom-in').remove();
  });
    document.getElementsByClassName(bg)[0].addEventListener(event, function() {
      document.getElementById('image-zoom-in').remove();
  });
}


/***********************************
 DOWNLOADING MODELING TOOLS AS CSV/JSON
************************************/
const rows = [];
let csvRows = "data:text/csv;charset=utf-8,";
let csvRowsArr = [];

const modelingToolsRows = document.getElementById('modeling-tools-column').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
for (let i = 0; i < modelingToolsRows.length; i++) {
    const modelingToolCells = modelingToolsRows[i].getElementsByTagName('td');

    const mtJson =   {
        "name": modelingToolCells[0].children[0].innerHTML.replace(/\n/g, "").trim(),
        "link": modelingToolCells[0].children[0].getAttribute('href'),
        "openSource": evaluateTableValue(modelingToolCells[1].innerHTML),
        "technology": evaluateTableValue(modelingToolCells[2].innerHTML.replace(/\n/g, "").trim()),
        "webApp": evaluateTableValue(modelingToolCells[3].innerHTML),
        "desktopApp": evaluateTableValue(modelingToolCells[4].innerHTML),
        "category": evaluateTableValue(modelingToolCells[5].innerHTML),
        "modelingLanguages": evaluateTableValue(modelingToolCells[6].innerHTML.trim().replace(/\n/g, " ").replace(/\s+/g, ", ")),
        "sourceCodeGeneration": evaluateTableValue(modelingToolCells[7].innerHTML),
        "cloudService": evaluateTableValue(modelingToolCells[8].innerHTML),
        "license": evaluateTableValue(modelingToolCells[9].innerHTML),
        "loginRequired": evaluateTableValue(modelingToolCells[10].innerHTML),
        "realTimeCollab": evaluateTableValue(modelingToolCells[11].innerHTML.trim().replace(/\n/g, " ").replace(/\s+/g, ", ")),
        "creators": evaluateTableValue(modelingToolCells[12].innerHTML.trim().replace(/\n/g, "")),
        "platforms": evaluateTableValue(modelingToolCells[13].innerHTML.trim().replace(/\n/g, " ").replace(/\s+/g, ", ")),
        "programmingLanguages": evaluateTableValue(modelingToolCells[14].innerHTML.trim().replace(/\n/g, " ").replace(/\s+/g, ", "))
    };
    rows.push(mtJson);

    let csvRowArrTmp = [];
    for (let key in mtJson) {
        csvRowArrTmp.push(mtJson[key]);
    }
    csvRowsArr.push(csvRowArrTmp);
}

/**
 * Evaluates a data entry from within the "modeling tools"-table and checks if it is null or empty
 * @param value of the table cell
 * @returns {null|*} null if the entry is empty or corresponds to a null value, otherwise returns the value
 */
function evaluateTableValue(value) {
    return value === 'None' || value === '' ? null : value;
}

const csvDownload = document.getElementById('download-csv');
const jsonDownload = document.getElementById('download-json');

jsonDownload.addEventListener('click', () => {
    const exportName = "modeling_tools_test";
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(rows));
    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href",     dataStr);
    downloadAnchorNode.setAttribute("download", exportName + ".json");
    document.body.appendChild(downloadAnchorNode); // required for firefox
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
});

csvDownload.addEventListener('click', () => {
    downloadBlob(arrayToCsv(csvRowsArr), 'export.csv', 'text/csv;charset=utf-8;')
});

function arrayToCsv(data){
  return data.map(row =>
    row
    .map(String)  // convert every value to String
    .map(v => v.replaceAll('"', '""'))  // escape double quotes
    .map(v => `"${v}"`)  // quote it
    .join(';')  // comma-separated
  ).join('\r\n');  // rows starting on new lines
}

/** Download contents as a file
 * Source: https://stackoverflow.com/questions/14964035/how-to-export-javascript-array-info-to-csv-on-client-side
 */
function downloadBlob(content, filename, contentType) {
  // Create a blob
  const blob = new Blob([content], { type: contentType });
  const url = URL.createObjectURL(blob);

  // Create a link to download it
  const pom = document.createElement('a');
  pom.href = url;
  pom.setAttribute('download', filename);
  pom.click();
}


/***********************************
 FILTER MODELING TOOL TABLE COLUMNS
************************************/
// "Filter Options"
const filterOptions = document.getElementById('options').children[0];
const chevronDown = filterOptions.getElementsByClassName('bi-chevron-down')[0];
const chevronRight = filterOptions.getElementsByClassName('bi-chevron-right')[0];
switchFilteringOptionsDisplay();

filterOptions.addEventListener('click', () => {
    switchFilteringOptionsDisplay();
});

function switchFilteringOptionsDisplay() {
    const webModelingGrid = document.getElementsByClassName('web-modeling-grid')[0];
    const searchColumn = document.getElementsByClassName('search-column')[0];
    if (chevronDown.style.display === 'none') {
        searchColumn.style.display = null;
        webModelingGrid.style.display = null;
        chevronDown.style.display = 'block';
        chevronRight.style.display = 'none';
    } else {
        searchColumn.style.display = 'none';
        webModelingGrid.style.display = 'block';
        chevronDown.style.display = 'none';
        chevronRight.style.display = 'block';
    }
}

// "Filter Table Columns"
const filterTableColumns = document.getElementById('filter-columns');
const filterTableColumnsHeader = document.getElementById('filter-columns').children[0];
const filterTableColumnsChevronDown = filterTableColumns.getElementsByClassName('bi-chevron-down')[0];
const filterTableColumnsChevronRight = filterTableColumns.getElementsByClassName('bi-chevron-right')[0];
filterTableColumnsChevronRight.style.display = 'none';

filterTableColumnsHeader.addEventListener('click', () => {
    const filterColumns = document.getElementsByClassName('filter-columns')[0];
    if (filterTableColumnsChevronDown.style.display === 'none') {
        filterColumns.style.display = null;
        filterTableColumnsChevronDown.style.display = 'block';
        filterTableColumnsChevronRight.style.display = 'none';
    } else if (filterTableColumnsChevronRight.style.display === 'none') {
        filterColumns.style.display = 'none';
        filterTableColumnsChevronDown.style.display = 'none';
        filterTableColumnsChevronRight.style.display = 'block';
    } else {
        console.error('While expanding/collapsing the "Filter Table Columns"-block, neither of the expected if conditions were met!');
    }
});

/***********************************
 SETTING TABLE CSS PROPERTIES
************************************/
const openSourceCells = document.getElementsByClassName('td-open-source');
const webAppCells = document.getElementsByClassName('td-web-app');
const desktopAppCells = document.getElementsByClassName('td-desktop-app');
const sourceCodeGenerationCells = document.getElementsByClassName('td-source-code-generation');
const cloudServiceCells = document.getElementsByClassName('td-cloud-service-available');
const loginRequiredCells = document.getElementsByClassName('td-log-in-required');
const realTimeCollab = document.getElementsByClassName('td-real-time-collab');

setBooleanCellCssStyle(openSourceCells);
setBooleanCellCssStyle(webAppCells);
setBooleanCellCssStyle(desktopAppCells);
setBooleanCellCssStyle(sourceCodeGenerationCells);
setBooleanCellCssStyle(cloudServiceCells);
setBooleanCellCssStyle(loginRequiredCells);
setBooleanCellCssStyle(realTimeCollab);

/**
 * Sets the CSS properties of binary values.
 * @param cells HTML-element expected to have an adjusted style
 */
function setBooleanCellCssStyle(cells) {
    for (let i = 0; i < cells.length; i++) {
        const boolCell = cells[i];
        const innerContent = removeTrailingWhitespaces(boolCell.innerHTML);
        if (innerContent === 'True') {
            boolCell.classList.add('true-value');
        } else if (innerContent === 'False') {
            boolCell.classList.add('false-value');
        } else if (innerContent === 'Unknown') {
            boolCell.classList.add('unknown-value');
        }
    }
}

/**
 * Removes trailing spaces and newlines within a string.
 * @param value string expected to be trimmed
 * @returns {string} a trimmed string
 */
function removeTrailingWhitespaces(value) {
    return value.trim().replace(/\n/g, "")
}

/***********************************
 FILTERING TABLE COLUMNS
************************************/
const TableColumn = {
    openSource: 'open-source',
    technology: 'technology',
    webApp: 'web-app',
    desktopApp: 'desktop-app',
    category: 'category',
    modelingLanguages: 'modeling-languages',
    sourceCode: 'source-code-generation',
    cloudService: 'cloud-service-available',
    license: 'license',
    login: 'log-in-required',
    realTimeCollab: 'real-time-collab',
    creators: 'creators',
    platforms: 'platforms',
    programmingLanguages: 'programming-languages'
}

optimizeColumns();

const propertyColumns = document.getElementsByClassName('property-column');
for (let i = 0; i < propertyColumns.length; i++) {
    const property = propertyColumns[i];
    property.addEventListener('click', () => {
        const checkbox = property.getElementsByClassName('checkbox')[0];
        if (checkbox.classList.contains('checked')) {
            checkTableColumn(property.getAttribute('id').substring(9), false);
        } else {
            checkTableColumn(property.getAttribute('id').substring(9), true);
        }
    })
}

document.getElementById('optimize-columns').addEventListener('click', () => {
    optimizeColumns();
});

/**
 * Optimizes the amount of columns showed within the "Modeling Tools" table, meaning if it does not fit within the
 * screen, it gets hidden.
 */
function optimizeColumns() {
    const searchColumn = document.getElementsByClassName('search-column');
    const filterExpanded = searchColumn.length > 0 && searchColumn[0].style.display !== 'none' ? 310 : 0;
    const windowWidth = window.innerWidth - filterExpanded;

    if (windowWidth >= 1780) {
        setCheckedColumns(true, true, true, true, true, true, true, true, true);
    }
    if (windowWidth < 1780 && windowWidth >= 1700) {
        setCheckedColumns(true, true, true, true, true, true, true, true);
    }
    if (windowWidth < 1700 && windowWidth >= 1560) {
        setCheckedColumns(true, true, true, true, true, true, true);
    }
    if (windowWidth < 1560 && windowWidth >= 1430) {
        setCheckedColumns(true, true, true, true, true, true);
    }
    if (windowWidth < 1430 && windowWidth >= 1290) {
        setCheckedColumns(true, true, true, true, true);
    }
    if (windowWidth < 1290 && windowWidth >= 1180) {
        setCheckedColumns(true, true, true, true);
    }
    if (windowWidth < 1180 && windowWidth >= 1060) {
        setCheckedColumns(true, true, true);
    }
    if (windowWidth < 1060 && windowWidth >= 950) {
        setCheckedColumns(true, true);
    }
    if (windowWidth < 950 && windowWidth >= 850) {
        setCheckedColumns(true);
    }
    if (windowWidth < 850) {
        setCheckedColumns();
    }
}

/**
 * Sets which columns within the modeling tools table are going to be displayed.
 * @param openSource sets the visibility of the "Open Source"-column
 * @param technology sets the visibility of the "Technology"-column
 * @param webApp sets the visibility of the "Web App"-column
 * @param desktopApp sets the visibility of the "Desktop App"-column
 * @param category sets the visibility of the "Category"-column
 * @param modelingLanguages sets the visibility of the "Modeling Languages"-column
 * @param sourceCode sets the visibility of the "Source Code"-column
 * @param cloudService sets the visibility of the "Cloud Service"-column
 * @param license sets the visibility of the "License"-column
 * @param login sets the visibility of the "Login"-column
 * @param realTimeCollab sets the visibility of the "Real Time Collaboration"-column
 * @param creators sets the visibility of the "Creators"-column
 * @param platforms sets the visibility of the "Platforms"-column
 * @param programmingLanguages sets the visibility of the "Programming Languages"-column
 */
function setCheckedColumns(
    openSource = false,
    technology = false,
    webApp = false,
    desktopApp = false,
    category = false,
    modelingLanguages = false,
    sourceCode = false,
    cloudService = false,
    license = false,
    login = false,
    realTimeCollab = false,
    creators = false,
    platforms = false,
    programmingLanguages = false
)
{
    checkTableColumn(TableColumn.openSource, openSource);
    checkTableColumn(TableColumn.technology, technology);
    checkTableColumn(TableColumn.webApp, webApp);
    checkTableColumn(TableColumn.desktopApp, desktopApp);
    checkTableColumn(TableColumn.category, category);
    checkTableColumn(TableColumn.modelingLanguages, modelingLanguages);
    checkTableColumn(TableColumn.sourceCode, sourceCode);
    checkTableColumn(TableColumn.cloudService, cloudService);
    checkTableColumn(TableColumn.license, license);
    checkTableColumn(TableColumn.login, login);
    checkTableColumn(TableColumn.realTimeCollab, realTimeCollab);
    checkTableColumn(TableColumn.creators, creators);
    checkTableColumn(TableColumn.platforms, platforms);
    checkTableColumn(TableColumn.programmingLanguages, programmingLanguages);
}

/**
 * Hides or displays a column within the modeling tools table.
 * @param tableColumn column expected to be hidden or displayed
 * @param check sets the visibility (hide/display)
 */
function checkTableColumn(tableColumn, check) {
    const header = document.getElementsByClassName(`th-${tableColumn}`)[0];
    const cells = document.getElementsByClassName(`td-${tableColumn}`);
    if (check) {
        setTableColumnCheckCss(document.getElementById(`property-${tableColumn}`), true);
        header.style.display = null;
        for (let i = 0; i < cells.length; i++) {
            cells[i].style.display = null;
        }
    } else {
        setTableColumnCheckCss(document.getElementById(`property-${tableColumn}`), false);
        header.style.display = 'none';
        for (let i = 0; i < cells.length; i++) {
            cells[i].style.display = 'none';
        }
    }

    /**
     * Updates the CSS properties of a checkbox within the "Filter Table Columns" section.
     * @param property HTML-element, within the checkbox is contained
     * @param check sets the status of the checkbox, meaning checked or not
     */
    function setTableColumnCheckCss(property, check) {
        const checkbox = property.getElementsByClassName('checkbox')[0];
        const biCheck = property.getElementsByClassName('bi-check')[0];
        if (check) {
            checkbox.classList.add('checked');
            biCheck.style.display = null;
        } else {
            checkbox.classList.remove('checked');
            biCheck.style.display = 'none';
        }
    }
}

// Update the amount of found results
const resultAmount = document.getElementById('modeling-tool-amount');
resultAmount.innerHTML = `Found ${rows.length} results`;

/***********************************
 ORDERING TABLE COLUMNS
************************************/
// Array containing all modeling tools, ordering can be changed
const modelingToolRows = rows;

// Array containing filtered modeling tools
const modelingToolRowsFiltered = rows;

/*
const columnsSortedStatus = {
    'name': false,
    'openSource': false,
    'technology': false,
    'webApp': false,
    'desktopApp': false,
    'category': false,
    'modelingLanguages': false,
    'sourceCode': false,
    'cloudService': false,
    'license': false,
    'login': false,
    'realTimeCollab': false,
    'creators': false,
    'platforms': false,
    'programmingLanguages': false
}
 */
const columnsSortedStatus = {
    'name': false,
    'open-source': false,
    'technology': false,
    'web-app': false,
    'desktop-app': false,
    'category': false,
    'modeling-languages': false,
    'source-code-generation': false,
    'cloud-service-available': false,
    'license': false,
    'log-in-required': false,
    'real-time-collab': false,
    'creators': false,
    'platforms': false,
    'programming-languages': false
}

modelingToolsColumn = document.getElementById('modeling-tools-column');
tableHeaders = modelingToolsColumn.getElementsByTagName('th');
tableRows = modelingToolsColumn.getElementsByTagName('tr');
tableTBody = modelingToolsColumn.getElementsByTagName('tbody').item(0);

// 1. Collecting all table-rows (tr)
const tableRowElements = [];
const tableRowTagElements = modelingToolsColumn.getElementsByTagName('tr');
// We want to exclude table-header row
for (let i = 1; i < tableRowTagElements.length; i++) {
    tableRowElements.push(tableRowTagElements[i]);
}


for (let i = 0; i < tableHeaders.length; i++) {
    tableHeaders[i].addEventListener('click', () => {
        const headerClassNames = tableHeaders[i].classList;
        let headerName = '';
        for (let j = 0; j < headerClassNames.length; j++) {
            if (headerClassNames[j].includes('th-')) {
                headerName = headerClassNames[j];
            }
        }
        const headerAttribute = headerName.substring(3);
        sortColumns(headerAttribute, tableHeaders[i]);
    });
}

sortColumns('name', tableHeaders[0]);

/**
 * Sorts entries of a column ascending/descending
 * @param headerAttribute name of the header
 * @param tableHeader th-tag within the header of the "modeling tools"-table
 */
function sortColumns(headerAttribute, tableHeader) {
    tableRowElements.sort(function (a, b) {
        let elA;
        let elB;
        if (headerAttribute === 'name') {
            elA = a.getElementsByTagName('a').item(0).innerText.toLowerCase();
            elB = b.getElementsByTagName('a').item(0).innerText.toLowerCase();
        } else {
            elA = a.getElementsByClassName(`td-${headerAttribute}`)[0].innerText.toLowerCase();
            elB = b.getElementsByClassName(`td-${headerAttribute}`)[0].innerText.toLowerCase();
        }
        // TODO: Make unknowns always last in the table (probably should check if it is boolean value)
        for (let j = 0; j < tableHeaders.length; j++) {
            const allSortTriangles= tableHeaders[j].getElementsByClassName('bi-triangle-fill');
            for (let k = 0; k < allSortTriangles.length; k++) {
                allSortTriangles[k].style.removeProperty('opacity');
            }
        }
        const sortTriangle = tableHeader.getElementsByClassName('bi-triangle-fill');
        if (columnsSortedStatus[headerAttribute]) {
            sortTriangle[0].style.opacity = 0.5;
            sortTriangle[1].style.opacity = 1;
            return elA > elB ? -1 : (elA < elB ? 1 : 0);
        } else {
            sortTriangle[0].style.opacity = 1;
            sortTriangle[1].style.opacity = 0.5;
            return elA < elB ? -1 : (elA > elB ? 1 : 0);
        }
    });
    columnsSortedStatus[headerAttribute] = !columnsSortedStatus[headerAttribute];

    tableTBody.innerHTML = '';
    for (let j = 0; j < tableRowElements.length; j++) {
        tableTBody.appendChild(tableRowElements[j]);
    }
}
