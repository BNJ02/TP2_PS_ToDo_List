L’objectif principal de ce TP est de comprendre comment fonctionne la persistance des données sur Android via l’utilisation de base de données. De plus, vous aurez besoin de mobiliser des concepts importants d’Android : la navigation entre les activités, la transmission de données entre activités et l’affichage de liste avec un RecyclerView.

# 1 Description de l’application
## 1.1 Minimum requis
L’application aura deux écrans :
<br>&emsp;• Un écran qui liste toutes les tâches en mémoire et sur lequel on peut cocher les tâches effectuées
<br>&emsp;• Un écran qui permet d’ajouter une nouvelle tâche via un formulaire à remplir avec un bouton pour valider la saisie
La navigation du premier écran vers le deuxième écran ce fait par un bouton flottant ’+’. Puis lorsqu’on a validé la saisie du deuxième écran, l’application nous ramène sur le premier écran. L’application devra au minimum réaliser ces fonctionnalités.

## 1.2 Fonctionnalités supplémentaires
Il sera ensuite possible d’ajouter les fonctionnalités suivantes :
<br>&emsp;• Chaque tâche appartient à une catégorie (“Ménage”, “Course”, “Travail”, “Divers”, “Administratif”, ...) : la catégorie est sélectionnée dans une liste déroulante (Spinner) du formulaire et elle s’affiche par une couleur d’item sur le premier ´ecran.
<br>&emsp;• La possibilité d’éditer une tâche dans la liste : au clic d’un bouton (icône d’édition) sur l’item le deuxième écran s’affiche avec un formulaire pré-rempli, à la validation la tâche est ´editée.
<br>&emsp;• La possibilité de supprimer une tâche dans la liste : au clic d’un bouton (icône de suppression) sur l’item, l’item est supprim´e la liste est rafraîchie.
<br>&emsp;• La possibilité d’ajouter de nouvelles catégories.
