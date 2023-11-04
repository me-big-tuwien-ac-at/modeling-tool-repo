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

// TODO: Finish
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


// TODO: Finish
/***********************************
 DOWNLOADING MODELING TOOLS AS CSV/JSON
************************************/
const modelingToolsRows = document.getElementById('modeling-tools-column').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
for (let i = 0; i < modelingToolsRows.length; i++) {
    const modelingToolCells = modelingToolsRows[i].getElementsByTagName('td');
    for (let j = 0; j < modelingToolCells.length; j++) {
        // console.log(modelingToolCells[j]);
    }
}
