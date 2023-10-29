/***********************************
 THEME
************************************/
// Setting the size of the theme switches to an adjustable size
const moonFill = document.getElementById('moon-fill');
const sunFill = document.getElementById('sun-fill');
const body = document.getElementsByTagName('body')[0];

// Switch between
moonFill.onclick = () => {
    moonFill.style.display = 'none';
    sunFill.style.display = 'list-item';
    body.style.backgroundColor = 'var(--dark-theme)';
    body.style.color = '#FFF';

}

sunFill.onclick = () => {
    moonFill.style.display = 'list-item';
    sunFill.style.display = 'none';
    body.style.backgroundColor = 'var(--light-theme)';
    body.style.color = '#000';
}

/**
 * Returns a CSS property of an HTML-element based on its id.
 *
 * @param id of an HTML-element
 * @param property name of the CSS property
 * @returns {string} the corresponding value of the CSS property
 */
function getCssPropertyById(id, property) {
    const element = document.getElementById(id);
    const style = window.getComputedStyle(element);
    return style.getPropertyValue(property);
}