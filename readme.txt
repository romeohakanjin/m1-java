1/Rajouter la datasource au fichier standalone-full, en modifiant l'ip pour qu'elle corresponde � celle de votre pc

<datasource jndi-name="java:jboss/datasources/rsmProject" pool-name="rsmProject" enabled="true" use-java-context="true">
  <connection-url>jdbc:mysql://192.168.150.1:3306/apocalypse</connection-url>
  <driver>mysql</driver>
    <security>
      <user-name>interro</user-name>
      <password>interro</password>
    </security>
</datasource>

2/D�veloppement

Chaque personne travaille uniquement sur sa branche pour ne pas cr�er de conflit entre les fichiers
La branche master doit uniquement contenir les fonctionnalit�s finalis�s (approuv� par tous, et tester)
Aucun fichier d'environnement, "personnel" (g�n�r� par eclipse) ne doit �tre commit.
Ne pas travailler en m�me temps sur un m�me fichier pour faciliter le merge de la branche dev � la branche master.

3/Convention
Les noms de fichiers, variables et m�thodes doivent �tre :
- explicite
- en anglais
- en camel : premier mot de la variable en miniscule, chaque premi�re lettre des autres mots en majuscule
	EX: nomVariableCamel

4/Guide de r�cup�ration du projet

Pr�-requis : Avoir install� git
A- Cr�ez un r�pertoire sur votre pc
B- Clique-droit dans votre nouveau r�pertoire, Cliquez sur "Git Bash Here"
C- Saisissez les commandes suivants :
	"git init"
	"git remote add origin https://github.com/romeohakanjin/m1-java.git"
	"git pull origin master"
	"git fetch"
D- Pour commencer � travailler sur votre branche : "git checkout [Nom de la branche]"
