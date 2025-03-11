# -*- coding: utf-8 -*-
"""
Created on Tue Mar 11 08:37:57 2025

@author: rapha
"""
from fonctions import *
num_tab = int (input ("Veuillez selectionner un tableau de contrainte entre 1 et 14"))
while num_tab<1 or num_tab>14:
    num_tab = int (input ("Veuillez selectionner un tableau de contrainte entre 1 et 14"))
#num_tab = 5

tab_contraintes = lecture(num_tab)
affichage_tab_contraintes(tab_contraintes)

graphe = transfo_graphe(num_tab)

if est_cyclique(graphe) or arc_negatif(graphe):
    print("Désolé, nous ne pouvons pas travailler sur ce graphe car il ne répond pas aux critères déligibilités")
else:
    pass