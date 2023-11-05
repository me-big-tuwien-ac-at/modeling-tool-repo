/***********************************
 THEME
************************************/
const bodyElement = document.getElementsByTagName('body')[0];
// TODO (Optional): Dynamically add svg's, instead of switching between "display: none;"
const sunSvg = document.getElementsByClassName('bi-sun-fill')[0];
const moonSvg = document.getElementsByClassName('bi-moon-fill')[0];
const images = document.getElementsByClassName('image');
setImageTheme();

moonSvg.addEventListener('click', () => {
   setImageTheme();
});

sunSvg.addEventListener('click', () => {
   setImageTheme();
});

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
        csvRowArrTmp.push(mtJson[key]) // += mtJson[key] + (key === 'programmingLanguages' ? "\n" : ";");
    }
    csvRowsArr.push(csvRowArrTmp);
}
console.log(csvRows);

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

setBooleanCellCssStyle(openSourceCells);
setBooleanCellCssStyle(webAppCells);
setBooleanCellCssStyle(desktopAppCells);
setBooleanCellCssStyle(sourceCodeGenerationCells);
setBooleanCellCssStyle(cloudServiceCells);
setBooleanCellCssStyle(loginRequiredCells);

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

function removeTrailingWhitespaces(value) {
    return value.trim().replace(/\n/g, "")
}