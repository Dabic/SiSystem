var express = require('express');
var router = express.Router();
var axios = require('axios');


const arangojs = require('arangojs');
const host = '64.225.110.65';
const port = '8529';
const username = 'tim_403_3_arango_si2019';
const password = 'sfr7Dej3';
const databasename = 'tim_403_3_arango_si2019';

const db = new arangojs.Database({
    url: `http://${username}:${password}@${host}:${port}`,
    databaseName: false
});

db.useBasicAuth(username, password);
db.useDatabase(databasename);

router.post('/compare/', function (req, res, next) {
    db.query('for info in competence_info return info')
        .then(infoResult => {
            let end = 0;
            const info = infoResult._result[0];
            if (infoResult._result[0].databasePopulated === 'false') {
                data = {
                    scheme: info.scheme,
                    query: info.query,
                    fieldCount: info.fieldCount,
                    databaseType: info.databaseType
                };
                axios.post('http://localhost:8084/heavy-client/transform/', data)
                    .then(res2 => {
                        end = res2.data.length;
                        let start = 0;
                        for (let i = 0; i < res2.data.length; i++) {
                            const someObj = res2.data[i];
                            setTimeout(() => {
                                db.query(`for course in Courses filter course.name == '${someObj.course}' return course`)
                                    .then(courseResult => {
                                        if (courseResult._result.length === 0) { // KURS NE POSTOJI
                                            db.query(`insert {name: '${someObj.course}'} into Courses return NEW`)
                                                .then(newCourseResult => {
                                                    const course = newCourseResult._result[0];
                                                    db.query(`for general in General_Competences filter general.name == '${someObj.general}' return general`)
                                                        .then(generalResult => {
                                                            if (generalResult._result.length === 0) { // GENERAL NE POSTOJI
                                                                db.query(`insert {name: '${someObj.general}'} into General_Competences return NEW`)
                                                                    .then(newGeneralResult => {
                                                                        const general = newGeneralResult._result[0];
                                                                        const week = Math.floor(Math.random() * 10);
                                                                        db.query(`insert {_from: '${course._id}', _to: '${general._id}', competences: '${someObj.competence}', weeks: '${week}'} into Competences return NEW`)
                                                                            .then(newCompetenceResult => {
                                                                                start++;
                                                                            })

                                                                    })
                                                            } else { // GENERAL POSTOJI
                                                                const general = generalResult._result[0];
                                                                const week = Math.floor(Math.random() * 10);
                                                                db.query(`insert {_from: '${course._id}', _to: '${general._id}', competences: '${someObj.competence}', weeks: '${week}'} into Competences return NEW`)
                                                                    .then(newCompetenceResult => {
                                                                        start++;
                                                                    })
                                                            }
                                                        })
                                                })
                                        } else { // KURS POSTOJI
                                            const course = courseResult._result[0];
                                            db.query(`for general in General_Competences filter general.name == '${someObj.general}' return general`)
                                                .then(generalResult => {
                                                    if (generalResult._result.length === 0) { // GENERAL NE POSTOJI
                                                        db.query(`insert {name: '${someObj.general}'} into General_Competences return NEW`)
                                                            .then(newGeneralResult => {
                                                                const general = newGeneralResult._result[0];
                                                                const week = Math.floor(Math.random() * 10);
                                                                db.query(`insert {_from: '${course._id}', _to: '${general._id}', competences: '${someObj.competence}', weeks: '${week}'} into Competences return NEW`)
                                                                    .then(newCompetenceResult => {
                                                                        start++;
                                                                    })
                                                            })
                                                    } else { // GENERAL POSTOJI
                                                        const general = generalResult._result[0];
                                                        console.log(general, course);
                                                        db.query(`for comp in Competences filter comp._from == '${course._id}' filter comp._to == '${general._id}' return comp`)
                                                            .then(competenceResult => {
                                                                if (competenceResult._result.length === 0) { // COMPETENCE NE POSTOJI
                                                                    console.log('stigao');
                                                                    const week = Math.floor(Math.random() * 10);
                                                                    db.query(`insert {_from: '${course._id}', _to: '${general._id}', competences: '${someObj.competence}', weeks: '${week}'} into Competences return NEW`)
                                                                        .then(newCompetenceResult => {
                                                                            start++;
                                                                        })
                                                                } else { // COMPETENCE POSTOJI
                                                                    const competence = competenceResult._result[0];
                                                                    let competences = competence.competences;
                                                                    console.log(competences);
                                                                    if (competences.split(',').filter(comp => comp === someObj.competence).length === 0) {
                                                                        competences = competences.concat(`,${someObj.competence}`);
                                                                    }
                                                                    console.log(competence._id);
                                                                    db.query(`update {_key: '${competence._key}', competences: '${competences}'} in Competences`)
                                                                        .then(newCompetence => {
                                                                            start++;
                                                                        })
                                                                }
                                                            })
                                                    }
                                                })
                                        }
                                    });
                            }, 100 * i)
                        }
                        let interval = setInterval(() => {
                            if (start === end) {
                                clearInterval(interval);
                                db.query(`update {_key: '${info._key}', databasePopulated: 'true'} in competence_info`).then(infoRes => {
                                    test(req, res)
                                })

                            }
                        }, 100)
                    });
            } else {
                test(req, res)
            }
        });

});

async function test(req, res) {
    const courses = req.body;
    const resultArr = [];
    let resultObj = {
        course: null,
        generals: [],
        competences: [],
        weeks: 0
    };
    for (let c of courses) {
        resultObj = {
            course: null,
            myCompetences: []
        };
        let coursesResult = await db.query(`for c in Courses filter c.name == '${c.course}' return c`);
        coursesResult = coursesResult._result[0];
        resultObj.course = coursesResult.name;
        let competencesResult = await db.query(`for c in Competences filter c._from == '${coursesResult._id}' return c`);

        for (let comp of competencesResult._result) {
            const competencesRes = [];
            comp.competences.split(',').forEach(c => {
                if (competencesRes.filter(v => v === c).length === 0) {
                    competencesRes.push({competence: c, weeks: comp.weeks});
                }
            });
            let general = await db.query(`for g in General_Competences filter g._id == '${comp._to}' return g`);
            const generalRes = [];
            for (let gen of general._result) {
                if (generalRes.filter(v => v === gen.name).length === 0) {
                    generalRes.push(gen.name);
                }
            }
            resultObj.myCompetences.push({general: {name: generalRes[0], weeks: competencesRes[0].weeks}, competences: competencesRes})
            // resultArr.push(resultObj);

        }
        resultArr.push(resultObj);
    }
    res.send(resultArr);
}

module.exports = router;
