package Cliente;



class Contato{    	
	private String endereco;
        private String apelido;
        private String nome;
	private String porta;
	
	public Contato(String endereco, String porta, String apelido, String nome) {
		super();
		this.endereco = endereco;
		this.porta = porta;
                this.apelido= apelido;
                this.nome = nome;
	}
        public Contato(String apelido, String nome) {
		super();
                this.apelido= apelido;
                this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}

	public String getPorta() {
		return porta;
	}
        public String getNome() {
		return nome;
	}
        public String getApelido() {
		return apelido;
	}

	public String getHash(){
		return (apelido + nome);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Contato){
			return ((Contato)obj).getHash().equals(getHash());
		}
		return false;
	}
}