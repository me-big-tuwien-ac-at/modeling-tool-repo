/***********************************
 CREATE/EDIT MODELING TOOL
************************************/
const createEditModelingToolWebSection = document.getElementById('modeling-tool-suggestion-update-web');
const createEditModelingToolMobileSection = document.getElementById('modeling-tool-suggestion-update-mobile');

adaptHeaderToPhoneOrWebMode();

window.addEventListener('resize', function(event){
    adaptHeaderToPhoneOrWebMode();
});

/**
 * Adapts CSS properties based on the window width.
 */
function adaptHeaderToPhoneOrWebMode() {
    if (window.innerWidth <= 1400) {
        createEditModelingToolWebSection.style.display = 'flex';
        createEditModelingToolMobileSection.style.display = 'none';
    } else {
        createEditModelingToolWebSection.style.display = 'none';
        createEditModelingToolMobileSection.style.display = 'flex';
    }
}

/***********************************
 THEME
************************************/
// Setting the size of the theme switches to an adjustable size
const moonFill = document.getElementById('moon-fill').children[0];
const sunFill = document.getElementById('sun-fill').children[0];
const body = document.getElementsByTagName('body')[0];

const Theme = {
    Light: 0,
    Dark: 1
}

if (localStorage.getItem('theme') === null || localStorage.getItem('theme') === 'light') {
    sunFill.style.display = 'none';
    setTheme(Theme.Light);
} else {
    moonFill.style.display = 'none';
    setTheme(Theme.Dark);
}

// Switch between
moonFill.onclick = () => {
    setTheme(Theme.Dark);
}

sunFill.onclick = () => {
    setTheme(Theme.Light);
}

function setTheme(theme) {
    if (theme === Theme.Light) {
        moonFill.style.display = 'list-item';
        sunFill.style.display = 'none';
        body.style.backgroundColor = 'var(--light-theme)';
        body.style.color = '#000';
        localStorage.setItem('theme', 'light');
    } else if (theme === Theme.Dark) {
        moonFill.style.display = 'none';
        sunFill.style.display = 'list-item';
        body.style.backgroundColor = 'var(--dark-theme)';
        body.style.color = '#FFF';
        localStorage.setItem('theme', 'dark');
    } else {
        console.error('While setting the theme, neither of the expected if conditions were met!');
    }
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