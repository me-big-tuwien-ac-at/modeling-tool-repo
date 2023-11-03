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
            informationHeader[i].nextElementSibling.style.display = 'block';
            chevronDown.style.display = 'block';
            chevronRight.style.display = 'none';
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
const bodyElement = document.getElementsByTagName('body')[0];
const modelingToolExamples = document.getElementsByClassName('modeling-tool-example');

for (let i = 0; i < modelingToolExamples.length; i++) {
    modelingToolExamples[i].addEventListener('click', function () {
        const zoomedInElement = document.createElement('div');
        zoomedInElement.setAttribute('id', 'image-zoom-in');
        zoomedInElement.setAttribute('class', 'image-zoom-in');
        zoomedInElement.innerHTML = `
          <span id="close">×</span>
          <div class="image-bg"></div>
          <img class="image-zoom" src="${modelingToolExamples[i].src}" alt="">
    `;

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
