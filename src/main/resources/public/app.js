const app = Vue.createApp({
    data() {
        return {
            selectedPokemonA: '',
            selectedPokemonB: '',
            pokemonName: '-----',
            hp: '-------'
        };
    },
    // computed: {
    //     formattedSelectedPokemonA: {
    //       get() {
    //         return this.selectedPokemonA;
    //       },
    //       set(value) {
    //         this.selectedPokemonA = value.toLowerCase();
    //       }
    //     },
    //     formattedSelectedPokemonB: {
    //       get() {
    //         return this.selectedPokemonB;
    //       },
    //       set(value) {
    //         this.selectedPokemonB = value.toLowerCase();
    //       }
    //     }
    //   },
    methods: {
        battle() {
            alert("Time to battle!");
            fetch(`/attack?pokemonA=${this.selectedPokemonA}&pokemonB=${this.selectedPokemonB}`)
                .then(response => response.json())
                .then(data => {
                    this.pokemonName = data.winner;
                    this.hp = data.hitPoints;
                    console.log(`This pokemon has ${data.hitPoints} hit points.`);
                })
                .catch(error => console.error('Error fetching battle result:', error));
        }
    }
});

app.mount('#app');