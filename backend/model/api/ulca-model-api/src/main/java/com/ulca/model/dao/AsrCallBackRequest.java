/*
package com.ulca.model.dao;

import io.swagger.model.ASRFiles;

public class AsrCallBackRequest {

	public AsrCallBackRequest() {
		super();
		this.config = new Config();
		this.audio = new ASRFiles();
	}
	public AsrCallBackRequest(Config config, ASRFiles audio) {
		super();
		this.config = config;
		this.audio = audio;
	}

	private Config config;
    private ASRFiles audio;
    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }
   
    
    
   public ASRFiles getAudio() {
		return audio;
	}
	public void setAudio(ASRFiles audio) {
		this.audio = audio;
	}



public  class Language {
        private String value;
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    
    public  class Config {
    	
		private Language language;
        private String transcriptionFormat;
        private String audioFormat;
        
        public Config() {
			super();
			// TODO Auto-generated constructor stub
			this.language = new Language();
			this.transcriptionFormat = "";
			this.audioFormat = "";
			
		}
        
        public Language getLanguage() {
            return language;
        }
        public void setLanguage(Language language) {
            this.language = language;
        }
        public String getTranscriptionFormat() {
            return transcriptionFormat;
        }
        public void setTranscriptionFormat(String transcriptionFormat) {
            this.transcriptionFormat = transcriptionFormat;
        }
        public String getAudioFormat() {
            return audioFormat;
        }
        public void setAudioFormat(String audioFormat) {
            this.audioFormat = audioFormat;
        }
    }
     
    
     public class Audio {
    	 
    	    private String audioUri;
    	    private byte[] audioContent;
    	    
    	    public byte[] getAudioContent() {
				return audioContent;
			}
			public void setAudioContent(byte[] audioContent) {
				this.audioContent = audioContent;
			}
			public String getAudioUri() {
    	        return audioUri;
    	    }
    	    public void setAudioUri(String audioUri) {
    	        this.audioUri = audioUri;
    	    }
    	}
    	
}
*/
