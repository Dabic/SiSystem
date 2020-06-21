import {CompareCompetencesGeneralModel} from './compare-competences-general.model';
import {CompareCompetencesCompetenceModel} from './compare-competences-competence.model';

export class CompareCompetenceMainModel {
    constructor(public general: CompareCompetencesGeneralModel, public competences: CompareCompetencesCompetenceModel[]) {
    }
}
