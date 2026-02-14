import getDatos from "./getDatos.js";

const params = new URLSearchParams(window.location.search);
const serieId = params.get('id');
const listaTemporadas = document.getElementById('temporadas-select');
const fichaSerie = document.getElementById('temporadas-episodios');
const fichaDescripcion = document.getElementById('ficha-descripcion');
const temporadasCardsContainer = document.getElementById('temporadas-cards');
const episodiosSection = document.getElementById('episodios-section');
const episodiosTitle = document.getElementById('episodios-title');
const btnVolverTemporadas = document.getElementById('btn-volver-temporadas');

// Funcion para cargar temporadas como cards
function cargarTemporadas() {
    getDatos(`/series/${serieId}/temporadas/todas`)
        .then(data => {
            const temporadasUnicas = [...new Set(data.map(temporada => temporada.temporada))];
            temporadasUnicas.sort((a, b) => a - b);
            
            // Limpiar select (mantenemos para compatibilidad)
            listaTemporadas.innerHTML = '';
            const optionDefault = document.createElement('option');
            optionDefault.value = '';
            optionDefault.textContent = 'Seleccione la temporada';
            listaTemporadas.appendChild(optionDefault);
            
            temporadasUnicas.forEach(temporada => {
                const option = document.createElement('option');
                option.value = temporada;
                option.textContent = temporada;
                listaTemporadas.appendChild(option);
            });

            const optionTodos = document.createElement('option');
            optionTodos.value = 'todas';
            optionTodos.textContent = 'Todas las temporadas';
            listaTemporadas.appendChild(optionTodos);

            // Generar cards de temporadas
            temporadasCardsContainer.innerHTML = '';
            
            temporadasUnicas.forEach((temporada, index) => {
                const card = document.createElement('div');
                card.className = 'temporada-card';
                card.style.animationDelay = `${index * 0.08}s`;
                card.style.animation = 'cardEnter 0.4s ease both';
                card.innerHTML = `
                    <span class="temporada-card__icon material-symbols-outlined">theaters</span>
                    <span class="temporada-card__number">${temporada}</span>
                    <span class="temporada-card__label">Temporada</span>
                `;
                card.addEventListener('click', () => seleccionarTemporada(temporada, card));
                temporadasCardsContainer.appendChild(card);
            });

            // Card para "Todas las temporadas"
            const cardTodas = document.createElement('div');
            cardTodas.className = 'temporada-card temporada-card--todas';
            cardTodas.style.animationDelay = `${temporadasUnicas.length * 0.08}s`;
            cardTodas.style.animation = 'cardEnter 0.4s ease both';
            cardTodas.innerHTML = `
                <span class="temporada-card__icon material-symbols-outlined">playlist_play</span>
                <span class="temporada-card__number">∞</span>
                <span class="temporada-card__label">Todas</span>
            `;
            cardTodas.addEventListener('click', () => seleccionarTemporada('todas', cardTodas));
            temporadasCardsContainer.appendChild(cardTodas);
        })
        .catch(error => {
            console.error('Error al obtener temporadas:', error);
        });
}

// Función para seleccionar una temporada
function seleccionarTemporada(temporada, cardElement) {
    // Marcar card activa
    document.querySelectorAll('.temporada-card').forEach(c => c.classList.remove('active'));
    cardElement.classList.add('active');
    
    // Actualizar select oculto y disparar carga
    listaTemporadas.value = temporada;
    
    // Actualizar título
    if (temporada === 'todas') {
        episodiosTitle.innerHTML = `
            <span class="material-symbols-outlined">movie</span>
            Todos los episodios
        `;
    } else {
        episodiosTitle.innerHTML = `
            <span class="material-symbols-outlined">movie</span>
            Temporada ${temporada} - Episodios
        `;
    }
    
    // Mostrar sección episodios
    episodiosSection.style.display = 'block';
    episodiosSection.style.animation = 'fadeInUp 0.4s ease both';
    
    // Scroll suave a la sección
    setTimeout(() => {
        episodiosSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
    
    cargarEpisodios();
}

// Botón volver a temporadas
if (btnVolverTemporadas) {
    btnVolverTemporadas.addEventListener('click', () => {
        episodiosSection.style.display = 'none';
        document.querySelectorAll('.temporada-card').forEach(c => c.classList.remove('active'));
        document.querySelector('.temporadas-section').scrollIntoView({ behavior: 'smooth', block: 'start' });
    });
}

// Funcion para cargar episodios de una temporada
function cargarEpisodios() {
    getDatos(`/series/${serieId}/temporadas/${listaTemporadas.value}`)
        .then(data => {
            const temporadasUnicas = [...new Set(data.map(temporada => temporada.temporada))];
            temporadasUnicas.sort((a, b) => a - b);
            fichaSerie.innerHTML = '';
            
            temporadasUnicas.forEach(temporada => {
                const episodiosTemporadaActual = data.filter(serie => serie.temporada === temporada);
                
                // Separador de temporada (solo si hay múltiples)
                if (temporadasUnicas.length > 1) {
                    const separator = document.createElement('div');
                    separator.className = 'temporada-separator';
                    separator.innerHTML = `<h4><span class="material-symbols-outlined" style="font-size:0.9em;">folder_open</span> Temporada ${temporada}</h4>`;
                    fichaSerie.appendChild(separator);
                }
                
                // Cards de episodios
                episodiosTemporadaActual.forEach((serie, index) => {
                    const card = document.createElement('div');
                    card.className = 'episodio-card';
                    card.style.animationDelay = `${index * 0.05}s`;
                    card.innerHTML = `
                        <div class="episodio-card__number">${serie.numeroEpisodio || index + 1}</div>
                        <div class="episodio-card__info">
                            <div class="episodio-card__title">${serie.titulo}</div>
                            <div class="episodio-card__meta">Temporada ${serie.temporada} · Episodio ${serie.numeroEpisodio || index + 1}</div>
                        </div>
                    `;
                    fichaSerie.appendChild(card);
                });
            });
        })
        .catch(error => {
            console.error('Error al obtener episodios:', error);
        });
}

// Funcion para cargar informaciones de la serie
function cargarInfoSerie() {
    getDatos(`/series/${serieId}`)
        .then(data => {
            fichaDescripcion.innerHTML = `
                <img src="${data.poster}" alt="${data.titulo}" />
                <div>
                    <h2>${data.titulo}</h2>
                    <div class="descricao-texto">
                        <p><b>Média de evaluaciones:</b> ★ ${data.evaluacion}</p>
                        <p>${data.sinopsis}</p>
                        <p><b>Actores:</b> ${data.actores}</p>
                    </div>
                </div>
            `;
        })
        .catch(error => {
            console.error('Error al obtener informaciones de la serie:', error);
        });
}

// Adiciona escuchador de evento para el elemento select (compatibilidad)
listaTemporadas.addEventListener('change', cargarEpisodios);

// Carga las informaciones de la série y las temporadas cuando la página carga
cargarInfoSerie();
cargarTemporadas();
