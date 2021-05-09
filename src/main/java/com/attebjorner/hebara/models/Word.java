package com.attebjorner.hebara.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wordforms")
public class Word
{
    @Id
    @SequenceGenerator(name = "wordform_sequence", sequenceName = "wordform_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordform_sequence")
    private long id;

    @Column(name = "wordform")
    private String word;

    private String lemma;

    private String pos;

    @ElementCollection
    @CollectionTable(
            name = "wordform_gram",
            joinColumns = {@JoinColumn(name = "wordform_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "gram_feature")
    @Column(name = "gram_value")
    private Map<String, String> gram;

    private String translation;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "sentences_wordforms",
            joinColumns = @JoinColumn(name = "wordform_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sentence_id", nullable = false)
    )
    @JsonIgnore
    private Set<Sentence> sentences;

    @Enumerated
    @Column(columnDefinition = "int")
    private LanguageType lang;

    public Word()
    {
    }

    public Word(String wordForm, String lemma, String pos, String translation, LanguageType lang)
    {
        this.word = wordForm;
        this.lemma = lemma;
        this.pos = pos;
        this.translation = translation;
        this.lang = lang;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getLemma()
    {
        return lemma;
    }

    public void setLemma(String lemma)
    {
        this.lemma = lemma;
    }

    public String getPos()
    {
        return pos;
    }

    public void setPos(String pos)
    {
        this.pos = pos;
    }

    public Map<String, String> getGram()
    {
        return gram;
    }

    public void setGram(Map<String, String> gram)
    {
        this.gram = gram;
    }

    public String getTranslation()
    {
        return translation;
    }

    public void setTranslation(String translation)
    {
        this.translation = translation;
    }

    public Set<Sentence> getSentences()
    {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences)
    {
        this.sentences = sentences;
    }

    public LanguageType getLang()
    {
        return lang;
    }

    public void setLang(LanguageType lang)
    {
        this.lang = lang;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return id == word1.id
                && Objects.equals(word, word1.word)
                && Objects.equals(lemma, word1.lemma)
                && Objects.equals(pos, word1.pos)
                && Objects.equals(gram, word1.gram)
                && Objects.equals(translation, word1.translation)
                && Objects.equals(sentences, word1.sentences)
                && lang == word1.lang;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, word, lemma, pos, gram, translation, sentences, lang);
    }
}
