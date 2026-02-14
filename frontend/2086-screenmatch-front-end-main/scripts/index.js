import getdatos from "./getDatos.js";

// Mapea los elementos DOM que desea actualizar
const elementos = {
    top5: document.querySelector('[data-name="top5"]'),
    lanzamientos: document.querySelector('[data-name="lanzamientos"]'),
    series: document.querySelector('[data-name="series"]')
};

// Almacén global de todas las series cargadas para búsqueda
let todasLasSeries = [];

// Funcion para crear la lista de peliculas
function crearListaPeliculas(elemento, datos) {
    const ulExistente = elemento.querySelector('ul');
    if (ulExistente) {
        elemento.removeChild(ulExistente);
    }

    const ul = document.createElement('ul');
    ul.className = 'lista';
    const isCarrusel = elemento.dataset.name === 'lanzamientos';
    const datosRender = isCarrusel ? [...datos, ...datos, ...datos] : datos;
    if (isCarrusel) {
        ul.className = 'carrusel-track';
    }
    const listaHTML = datosRender.map((pelicula) => `
        <li>
            <a href="detalles.html?id=${pelicula.id}">
                <img src="${pelicula.poster}" alt="${pelicula.titulo}">
            </a>
        </li>
    `).join('');

    ul.innerHTML = listaHTML;
    elemento.appendChild(ul);
}

// Funcion genérica para tratamiento de errores
function tratarConErrores(mensajeError) {
    console.error(mensajeError);
}

const categoriaSelect = document.querySelector('[data-categorias]');
const sectionsParaOcultar = document.querySelectorAll('.section');

categoriaSelect.addEventListener('change', function () {
    const categoria = document.querySelector('[data-name="categoria"]');
    const categoriaSeleccionada = categoriaSelect.value;

    if (categoriaSeleccionada === 'todos') {
        for (const section of sectionsParaOcultar) {
            section.classList.remove('hidden');
        }
        categoria.classList.add('hidden');
    } else {
        for (const section of sectionsParaOcultar) {
            section.classList.add('hidden');
        }
        categoria.classList.remove('hidden');
        getdatos(`/series/categoria/${categoriaSeleccionada}`)
            .then(data => {
                crearListaPeliculas(categoria, data);
            })
            .catch(error => {
                tratarConErrores("Ocurrio un error al cargar los datos de la categoria.");
            });
    }
});

// Array de URLs para las solicitudes
generaSeries();
function generaSeries() {
    const urls = ['/series/top5', '/series/lanzamientos', '/series'];

    Promise.all(urls.map(url => getdatos(url)))
        .then(data => {
            crearListaPeliculas(elementos.top5, data[0]);
            crearListaPeliculas(elementos.lanzamientos, data[1]);
            crearListaPeliculas(elementos.series, data[2].slice(0, 5));

            // Almacenar todas las series para búsqueda client-side
            const seriesMap = new Map();
            [data[0], data[1], data[2]].flat().forEach(serie => {
                if (serie && serie.id) {
                    seriesMap.set(serie.id, serie);
                }
            });
            todasLasSeries = Array.from(seriesMap.values());
        })
        .catch(error => {
            tratarConErrores("Ocurrio un error al cargar los datos.");
        });
}

// ==================== BÚSQUEDA ====================
const btnSearch = document.getElementById('btn-search');
const searchOverlay = document.getElementById('search-overlay');
const searchInput = document.getElementById('search-input');
const searchResults = document.getElementById('search-results');
const searchClose = document.getElementById('search-close');

btnSearch.addEventListener('click', () => {
    searchOverlay.classList.add('active');
    setTimeout(() => searchInput.focus(), 300);
});

searchClose.addEventListener('click', cerrarBusqueda);

searchOverlay.addEventListener('click', (e) => {
    if (e.target === searchOverlay) {
        cerrarBusqueda();
    }
});

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && searchOverlay.classList.contains('active')) {
        cerrarBusqueda();
    }
});

function cerrarBusqueda() {
    searchOverlay.classList.remove('active');
    searchInput.value = '';
    searchResults.innerHTML = `
        <div class="search-placeholder">
            <span class="material-symbols-outlined">movie_filter</span>
            <p>Escribe para buscar...</p>
        </div>
    `;
}

searchInput.addEventListener('input', () => {
    const query = searchInput.value.toLowerCase().trim();

    if (query.length === 0) {
        searchResults.innerHTML = `
            <div class="search-placeholder">
                <span class="material-symbols-outlined">movie_filter</span>
                <p>Escribe para buscar...</p>
            </div>
        `;
        return;
    }

    const resultados = todasLasSeries.filter(serie =>
        serie.titulo.toLowerCase().includes(query)
    );

    if (resultados.length === 0) {
        searchResults.innerHTML = `
            <div class="search-placeholder">
                <span class="material-symbols-outlined">search_off</span>
                <p>No se encontraron resultados para "<strong>${query}</strong>"</p>
            </div>
        `;
        return;
    }

    searchResults.innerHTML = resultados.map((serie, index) => `
        <a href="detalles.html?id=${serie.id}" class="search-result-item" style="animation-delay: ${index * 0.05}s">
            <img src="${serie.poster}" alt="${serie.titulo}" class="search-result-img">
            <div class="search-result-info">
                <h4>${resaltarTexto(serie.titulo, query)}</h4>
            </div>
            <span class="material-symbols-outlined search-result-arrow">arrow_forward</span>
        </a>
    `).join('');
});

function resaltarTexto(texto, query) {
    const regex = new RegExp(`(${query.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
    return texto.replace(regex, '<mark>$1</mark>');
}

// ==================== NOTIFICACIONES ====================
const btnNotif = document.getElementById('btn-notif');
const dropdownNotif = document.getElementById('dropdown-notif');
const dropdownProfile = document.getElementById('dropdown-profile');
const overlayBackdrop = document.getElementById('overlay-backdrop');

btnNotif.addEventListener('click', (e) => {
    e.stopPropagation();
    const isOpen = dropdownNotif.classList.contains('active');
    cerrarTodosDropdowns();
    if (!isOpen) {
        dropdownNotif.classList.add('active');
        overlayBackdrop.classList.add('active');
    }
});

// ==================== PERFIL / LOGIN / AVATARS ====================
const btnProfile = document.getElementById('btn-profile');
const headerAvatar = document.getElementById('header-avatar');
const avatarOptions = document.querySelectorAll('.avatar-option');

btnProfile.addEventListener('click', (e) => {
    e.stopPropagation();
    const isOpen = dropdownProfile.classList.contains('active');
    cerrarTodosDropdowns();
    if (!isOpen) {
        dropdownProfile.classList.add('active');
        overlayBackdrop.classList.add('active');
    }
});

// También permitir click en avatar del header para abrir perfil
headerAvatar.addEventListener('click', (e) => {
    e.stopPropagation();
    const isOpen = dropdownProfile.classList.contains('active');
    cerrarTodosDropdowns();
    if (!isOpen) {
        dropdownProfile.classList.add('active');
        overlayBackdrop.classList.add('active');
    }
});

avatarOptions.forEach(option => {
    option.addEventListener('click', (e) => {
        e.stopPropagation();
        const avatarSrc = option.getAttribute('data-avatar');

        // Marcar seleccionado
        avatarOptions.forEach(o => o.classList.remove('selected'));
        option.classList.add('selected');

        // Reemplazar icono por avatar
        btnProfile.style.display = 'none';
        headerAvatar.src = avatarSrc;
        headerAvatar.style.display = 'block';

        // Guardar en localStorage
        localStorage.setItem('selectedAvatar', avatarSrc);

        // Cerrar dropdown después de un breve momento
        setTimeout(() => cerrarTodosDropdowns(), 500);
    });
});

// Cargar avatar guardado
const savedAvatar = localStorage.getItem('selectedAvatar');
if (savedAvatar) {
    btnProfile.style.display = 'none';
    headerAvatar.src = savedAvatar;
    headerAvatar.style.display = 'block';

    // Marcar el avatar guardado como seleccionado
    avatarOptions.forEach(option => {
        if (option.getAttribute('data-avatar') === savedAvatar) {
            option.classList.add('selected');
        }
    });
}

// Cerrar dropdowns
overlayBackdrop.addEventListener('click', cerrarTodosDropdowns);

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        cerrarTodosDropdowns();
    }
});

function cerrarTodosDropdowns() {
    dropdownNotif.classList.remove('active');
    dropdownProfile.classList.remove('active');
    overlayBackdrop.classList.remove('active');
}
